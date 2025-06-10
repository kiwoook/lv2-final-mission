package finalmission.reservation.service;

import finalmission.common.exception.NotFoundException;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationStatus;
import finalmission.reservation.dto.request.ReservationCreateRequest;
import finalmission.reservation.dto.response.ReservationInfoResponse;
import finalmission.reservation.repository.ReservationRepository;
import finalmission.trainer.domain.Trainer;
import finalmission.trainer.repository.TrainerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final TrainerRepository trainerRepository;
    private final ReservationRepository reservationRepository;

    // 예약 날짜가 공휴일이면 생성할 수 없다.
    @Transactional
    public ReservationInfoResponse create(ReservationCreateRequest createRequest) {

        Trainer trainer = trainerRepository.findById(createRequest.trainerId())
                .orElseThrow(() -> new NotFoundException("트레이너가 존재하지 않습니다!"));

        Reservation reservation = Reservation.create(createRequest.reservationDateTime(), trainer);

        Reservation saved = reservationRepository.save(reservation);

        return ReservationInfoResponse.of(saved);
    }

    @Transactional(readOnly = true)
    public List<ReservationInfoResponse> getAvailableReservations() {
        return reservationRepository.findByStatus(ReservationStatus.AVAILABLE)
                .stream()
                .map(ReservationInfoResponse::of)
                .toList();
    }

    // TODO 예약하기

    // TODO 예약 취소

}
