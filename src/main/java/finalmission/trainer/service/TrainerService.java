package finalmission.trainer.service;

import finalmission.trainer.domain.Trainer;
import finalmission.trainer.dto.request.TrainerCreateRequest;
import finalmission.trainer.dto.response.TrainerInfoResponse;
import finalmission.trainer.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TrainerService {

    private final TrainerRepository trainerRepository;

    public TrainerInfoResponse create(TrainerCreateRequest createRequest) {
        Trainer trainer = new Trainer(createRequest.name(), createRequest.birth());

        Trainer saved = trainerRepository.save(trainer);

        return TrainerInfoResponse.of(saved);
    }
}
