package finalmission.trainer.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import finalmission.trainer.domain.Trainer;
import java.time.LocalDate;

public record TrainerInfoResponse(Long id, String name, @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birth) {

    public static TrainerInfoResponse of(Trainer trainer) {
        return new TrainerInfoResponse(trainer.getId(), trainer.getName(), trainer.getBirth());
    }
}
