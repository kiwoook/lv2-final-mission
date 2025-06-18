package finalmission.reservation.controller;

import finalmission.auth.dto.MemberInfo;
import finalmission.auth.resolver.Authenticated;
import finalmission.reservation.dto.request.ReserveChangeRequest;
import finalmission.reservation.dto.request.ReserveRequest;
import finalmission.reservation.dto.response.MyReservedInfoResponse;
import finalmission.reservation.dto.response.ReservationInfoResponse;
import finalmission.reservation.dto.response.ReservedInfoRequest;
import finalmission.reservation.service.ReservationService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<ReservedInfoRequest> reserve(@Valid @RequestBody ReserveRequest request,
                                                       @Authenticated MemberInfo memberInfo) {
        ReservedInfoRequest response = reservationService.reserve(request, memberInfo);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancel(@PathVariable Long id, @Authenticated MemberInfo memberInfo) {
        reservationService.cancel(id, memberInfo);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<ReservedInfoRequest> change(@Valid @RequestBody ReserveChangeRequest updateRequest,
                                                      @Authenticated MemberInfo memberInfo) {
        ReservedInfoRequest response = reservationService.change(updateRequest, memberInfo);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<List<MyReservedInfoResponse>> getAllMyReservations(@Authenticated MemberInfo memberInfo) {
        List<MyReservedInfoResponse> responses = reservationService.getAllReservationsByMember(
                memberInfo);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/available")
    public ResponseEntity<List<ReservationInfoResponse>> getAvailableReservations() {
        List<ReservationInfoResponse> responses = reservationService.getAvailableReservations();

        return ResponseEntity.ok(responses);
    }
}
