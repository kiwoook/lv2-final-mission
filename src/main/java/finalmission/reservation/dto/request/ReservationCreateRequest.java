package finalmission.reservation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record ReservationCreateRequest(
        @NotNull @JsonFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime reservationDateTime,
        @NotNull Long trainerId) {
}
