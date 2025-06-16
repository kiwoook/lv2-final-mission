package finalmission.auth.web.controller;

import finalmission.auth.web.controller.request.LoginRequest;
import finalmission.auth.dto.TokenDto;
import finalmission.auth.provider.CookieProvider;
import finalmission.auth.web.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;
    private final CookieProvider cookieProvider;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody @Valid LoginRequest loginRequest, HttpServletResponse response) {
        TokenDto tokenDto = authService.login(loginRequest);
        response.addCookie(cookieProvider.createTokenCookie(tokenDto));

        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        response.addCookie(cookieProvider.createExpiredTokenCookie());

        return ResponseEntity.ok().build();
    }
}
