package finalmission.reservation.controller;

import finalmission.auth.resolver.Authenticated;
import finalmission.reservation.dto.request.ReserveRequest;
import finalmission.reservation.dto.request.ReserveUpdateRequest;
import finalmission.reservation.dto.response.ReservationInfoResponse;
import finalmission.reservation.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/reservations")
@Controller
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationInfoResponse> reserve(@Valid @RequestBody ReserveRequest request,
                                                           @Authenticated Long memberId) {
        ReservationInfoResponse response = reservationService.reserve(request, memberId);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @Authenticated Long memberId) {
        reservationService.cancel(id, memberId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<ReservationInfoResponse> update(@Valid @RequestBody ReserveUpdateRequest updateRequest,
                                                          @Authenticated Long memberId) {

        ReservationInfoResponse response = reservationService.update(updateRequest, memberId);

        return ResponseEntity.ok(response);
    }

}
