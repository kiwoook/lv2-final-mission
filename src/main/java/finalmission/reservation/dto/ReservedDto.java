package finalmission.reservation.dto;

import finalmission.reservation.domain.Reservation;

public record ReservedDto(Long id, Reservation reservation) {
}
