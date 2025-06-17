package finalmission.reservation.controller;

import finalmission.reservation.dto.request.ReservationCreateRequest;
import finalmission.reservation.dto.response.ReservationInfoResponse;
import finalmission.reservation.service.ReservationService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/admin/reservations")
@Controller
public class ReservationAdminController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationInfoResponse> create(@Valid @RequestBody ReservationCreateRequest createRequest) {
        ReservationInfoResponse response = reservationService.create(createRequest);

        return ResponseEntity.status(HttpStatus.CREATED.value())
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ReservationInfoResponse>> getAll() {
        List<ReservationInfoResponse> responses = reservationService.getAll();

        return ResponseEntity.ok(responses);
    }
}
