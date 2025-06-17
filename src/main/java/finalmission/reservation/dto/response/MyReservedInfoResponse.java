package finalmission.reservation.dto.response;

import finalmission.reservation.domain.Reservation;

public record MyReservedInfoResponse(Long id, ReservationInfoResponse reservation) {

    public static MyReservedInfoResponse of(Long id, Reservation reservation) {
        return new MyReservedInfoResponse(
                id,
                ReservationInfoResponse.from(reservation)
        );
    }
}
