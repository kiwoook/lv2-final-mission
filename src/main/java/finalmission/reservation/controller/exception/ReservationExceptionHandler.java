package finalmission.reservation.controller.exception;

import finalmission.reservation.exception.InAlreadyReservationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ReservationExceptionHandler {

    @ExceptionHandler(InAlreadyReservationException.class)
    public ResponseEntity<String> handleInAlreadyReservationException() {
        return ResponseEntity.status(HttpStatus.CONFLICT.value())
                .body("이미 예약이 존재합니다!");
    }
}
