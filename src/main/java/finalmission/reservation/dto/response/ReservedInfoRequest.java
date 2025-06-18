package finalmission.reservation.dto.response;

import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.Reserved;

public record ReservedInfoRequest(Long id, ReservationInfoResponse reservation) {

    public static ReservedInfoRequest of(Reserved reserved, Reservation reservation) {
        return new ReservedInfoRequest(reserved.getId(), ReservationInfoResponse.from(reservation));
    }
}
