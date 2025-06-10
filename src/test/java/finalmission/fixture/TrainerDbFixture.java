package finalmission.fixture;

import finalmission.trainer.domain.Trainer;
import finalmission.trainer.repository.TrainerRepository;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class TrainerDbFixture {

    @Autowired
    private TrainerRepository trainerRepository;

    public Trainer createTrainer1() {
        Trainer trainer = new Trainer("꾹", LocalDate.now());

        return trainerRepository.save(trainer);
    }

}
