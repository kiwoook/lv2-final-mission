package finalmission.reservation.service;

import finalmission.common.exception.NotFoundException;
import finalmission.member.domain.Member;
import finalmission.member.repository.MemberRepository;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationStatus;
import finalmission.reservation.domain.Reserved;
import finalmission.reservation.dto.request.ReservationCreateRequest;
import finalmission.reservation.dto.request.ReserveRequest;
import finalmission.reservation.dto.request.ReserveUpdateRequest;
import finalmission.reservation.dto.response.ReservationInfoResponse;
import finalmission.reservation.exception.InAlreadyReservationException;
import finalmission.reservation.repository.ReservationRepository;
import finalmission.reservation.repository.ReservedRepository;
import finalmission.trainer.domain.Trainer;
import finalmission.trainer.repository.TrainerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReservationService {

    private final TrainerRepository trainerRepository;
    private final ReservationRepository reservationRepository;
    private final ReservedRepository reservedRepository;
    private final MemberRepository memberRepository;

    // TODO 예약 날짜가 공휴일이면 생성할 수 없다.
    @Transactional
    public ReservationInfoResponse create(ReservationCreateRequest createRequest) {
        validateDuplicateReservation(createRequest);

        Trainer trainer = trainerRepository.findById(createRequest.trainerId())
                .orElseThrow(() -> new NotFoundException("트레이너가 존재하지 않습니다!"));

        Reservation reservation = Reservation.create(createRequest.reservationDateTime(), trainer);

        Reservation saved = reservationRepository.save(reservation);

        return ReservationInfoResponse.of(saved);
    }

    private void validateDuplicateReservation(ReservationCreateRequest createRequest) {
        log.info("validate 접근 arg = {}", createRequest);
        if (reservationRepository.existsByReservationDateTimeAndTrainer_Id(createRequest.reservationDateTime(),
                createRequest.trainerId())) {
            throw new InAlreadyReservationException("이미 예약이 존재합니다.");
        }
    }

    @Transactional(readOnly = true)
    public List<ReservationInfoResponse> getAvailableReservations() {
        return reservationRepository.findByStatus(ReservationStatus.AVAILABLE)
                .stream()
                .map(ReservationInfoResponse::of)
                .toList();
    }

    @Transactional
    public ReservationInfoResponse reserve(ReserveRequest reserveRequest, Long memberId) {
        Reservation reservation = reservationRepository.findById(reserveRequest.id())
                .orElseThrow(() -> new NotFoundException("해당 예약이 존재하지 않습니다."));

        if (reservation.isNotAvailable()) {
            throw new InAlreadyReservationException("이미 사용 중인 예약입니다.");
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("해당 멤버를 찾을 수 없습니다."));

        Reserved reserved = new Reserved(member, reservation);
        reservedRepository.save(reserved);
        reservation.updateStatus(ReservationStatus.COMPLETE);

        return ReservationInfoResponse.of(reservation);
    }

    // TODO 예약 수정하기
    @Transactional
    public ReservationInfoResponse update(ReserveUpdateRequest updateRequest) {


        return null;
    }

    // TODO 예약 취소
    public ReservationInfoResponse cancel(){
        return null;
    }

}
