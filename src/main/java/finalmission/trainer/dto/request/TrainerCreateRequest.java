package finalmission.trainer.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record TrainerCreateRequest(@NotEmpty String name,
                                   @NotNull @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birth) {
}
