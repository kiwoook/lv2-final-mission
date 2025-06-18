package finalmission.reservation.dto.request;

public record ReserveChangeRequest(Long oldReservationId, Long newReservationId) {
}
