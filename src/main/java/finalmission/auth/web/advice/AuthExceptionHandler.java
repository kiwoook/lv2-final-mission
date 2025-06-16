package finalmission.auth.web.advice;

import finalmission.auth.exception.InvalidAuthorizationException;
import finalmission.auth.exception.TokenNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<Void> handleTokenNotFoundException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).build();
    }

    @ExceptionHandler(InvalidAuthorizationException.class)
    public ResponseEntity<Void> handleInvalidAuthorizationException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).build();
    }
}
