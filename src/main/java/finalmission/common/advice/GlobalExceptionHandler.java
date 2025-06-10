package finalmission.common.advice;

import finalmission.common.exception.InvalidArgumentException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<Void> handleInvalidArgumentException() {
        return ResponseEntity.badRequest().build();
    }
}
