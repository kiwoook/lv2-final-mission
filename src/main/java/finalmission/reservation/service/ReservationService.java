package finalmission.reservation.service;

import finalmission.auth.dto.MemberInfo;
import finalmission.common.exception.InvalidArgumentException;
import finalmission.common.exception.NotFoundException;
import finalmission.holiday.service.HolidayService;
import finalmission.member.domain.Member;
import finalmission.member.repository.MemberRepository;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationStatus;
import finalmission.reservation.domain.Reserved;
import finalmission.reservation.dto.request.ReservationCreateRequest;
import finalmission.reservation.dto.request.ReserveChangeRequest;
import finalmission.reservation.dto.request.ReserveRequest;
import finalmission.reservation.dto.response.MyReservedInfoResponse;
import finalmission.reservation.dto.response.ReservationInfoResponse;
import finalmission.reservation.dto.response.ReservedInfoRequest;
import finalmission.reservation.exception.InAlreadyReservationException;
import finalmission.reservation.exception.InvalidCreateReservationException;
import finalmission.reservation.repository.ReservationRepository;
import finalmission.reservation.repository.ReservedRepository;
import finalmission.trainer.domain.Trainer;
import finalmission.trainer.repository.TrainerRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final TrainerRepository trainerRepository;
    private final ReservationRepository reservationRepository;
    private final ReservedRepository reservedRepository;
    private final MemberRepository memberRepository;
    private final HolidayService holidayService;

    @Transactional
    public ReservationInfoResponse create(ReservationCreateRequest createRequest) {
        validateCreateReservation(createRequest);

        Trainer trainer = trainerRepository.findById(createRequest.trainerId())
                .orElseThrow(() -> new InvalidCreateReservationException("트레이너가 존재하지 않습니다!"));

        Reservation reservation = Reservation.create(createRequest.reservationDateTime(), trainer);

        Reservation saved = reservationRepository.save(reservation);

        return ReservationInfoResponse.from(saved);
    }

    private void validateCreateReservation(ReservationCreateRequest createRequest) {
        validateHoliday(createRequest);
        validateDuplicateReservation(createRequest);
    }

    private void validateHoliday(ReservationCreateRequest createRequest) {
        boolean holiday = holidayService.isHoliday(LocalDate.from(createRequest.reservationDateTime()));
        if (holiday) {
            throw new InvalidCreateReservationException("공휴일에는 예약을 생성할 수 없습니다!");
        }
    }

    private void validateDuplicateReservation(ReservationCreateRequest createRequest) {
        if (reservationRepository.existsByReservationDateTimeAndTrainerId(createRequest.reservationDateTime(),
                createRequest.trainerId())) {
            throw new InAlreadyReservationException("이미 예약이 존재합니다.");
        }
    }

    @Transactional(readOnly = true)
    public List<ReservationInfoResponse> getAll() {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationInfoResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ReservationInfoResponse> getAvailableReservations() {
        return reservationRepository.findByStatus(ReservationStatus.AVAILABLE)
                .stream()
                .map(ReservationInfoResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MyReservedInfoResponse> getAllReservationsByMember(MemberInfo memberInfo) {
        return reservedRepository.findReservedByMember(memberInfo.userId())
                .stream()
                .map(reservedDto -> MyReservedInfoResponse.of(reservedDto.id(), reservedDto.reservation()))
                .toList();
    }

    @Transactional
    public ReservedInfoRequest reserve(ReserveRequest reserveRequest, MemberInfo memberInfo) {
        return reserve(reserveRequest.id(), memberInfo);
    }

    @Transactional
    public ReservedInfoRequest change(ReserveChangeRequest updateRequest, MemberInfo memberInfo) {
        Long newReservationId = updateRequest.newReservationId();
        Long oldReservationId = updateRequest.oldReservationId();
        reserve(newReservationId, memberInfo);

        Member member = memberRepository.findById(memberInfo.userId())
                .orElseThrow(() -> new NotFoundException("해당 유저가 존재하지 않습니다. id = " + memberInfo.userId()));

        Reservation newReservation = getReservation(newReservationId);
        Reservation oldReservation = getReservation(oldReservationId);

        oldReservation.updateStatus(ReservationStatus.AVAILABLE);

        Reserved reserved = reservedRepository.findByReservationAndMember(oldReservation, member)
                .orElseThrow(() -> new NotFoundException("예약되지 않았습니다."));

        reserved.updateReservation(newReservation);

        return ReservedInfoRequest.of(reserved, newReservation);
    }

    private Reservation getReservation(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException("해당 예약이 존재하지 않습니다. id = " + reservationId));
    }

    private ReservedInfoRequest reserve(Long reservationId, MemberInfo memberInfo) {
        Reservation reservation = getReservation(reservationId);

        if (reservation.isNotAvailable()) {
            throw new InAlreadyReservationException("이미 사용 중인 예약입니다.");
        }

        Member member = memberRepository.findById(memberInfo.userId())
                .orElseThrow(() -> new NotFoundException("해당 멤버를 찾을 수 없습니다."));

        Reserved reserved = new Reserved(member, reservation);
        reservedRepository.save(reserved);

        return ReservedInfoRequest.of(reserved, reservation);
    }


    @Transactional
    public void cancel(Long reservedId, MemberInfo memberInfo) {
        Reserved reserved = reservedRepository.findById(reservedId)
                .orElseThrow(() -> new NotFoundException("예약되지 않았습니다."));

        if (!reserved.sameMember(memberInfo.userId())) {
            throw new InvalidArgumentException("해당 예약자가 아닙니다.");
        }

        Reservation reservation = reserved.getReservation();
        reservation.updateStatus(ReservationStatus.AVAILABLE);

        reservedRepository.delete(reserved);
    }
}
