package finalmission.reservation.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record ReservationCreateRequest(@NotNull LocalDateTime reservationDateTime, @NotNull Long trainerId) {
}
