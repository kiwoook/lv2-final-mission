package finalmission.trainer.controller;

import finalmission.trainer.dto.request.TrainerCreateRequest;
import finalmission.trainer.dto.response.TrainerInfoResponse;
import finalmission.trainer.service.TrainerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/trainers")
@Controller
public class TrainerController {

    private final TrainerService trainerService;

    @PostMapping
    public ResponseEntity<TrainerInfoResponse> addTrainer(@Valid @RequestBody TrainerCreateRequest createRequest) {
        TrainerInfoResponse response = trainerService.create(createRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
