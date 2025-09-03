package finalmission.reservation.dto.response;

import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationStatus;
import finalmission.trainer.dto.response.TrainerInfoResponse;
import java.time.LocalDateTime;

public record ReservationInfoResponse(Long id, LocalDateTime reservationDateTime, TrainerInfoResponse trainerInfo,
                                      ReservationStatus status) {

    public static ReservationInfoResponse from(Reservation reservation) {
        return new ReservationInfoResponse(
                reservation.getId(),
                reservation.getReservationDateTime(),
                TrainerInfoResponse.of(reservation.getTrainer()),
                reservation.getStatus());
    }
}
