package finalmission.auth.interceptor;

import finalmission.auth.exception.NotAdminException;
import finalmission.auth.exception.TokenNotFoundException;
import finalmission.auth.provider.CookieProvider;
import finalmission.auth.provider.JwtProvider;
import finalmission.member.domain.Roles;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class AdminHandlerInterceptor implements HandlerInterceptor {

    private final JwtProvider jwtProvider;
    private final CookieProvider cookieProvider;

    private static Cookie[] getCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new TokenNotFoundException("쿠키가 존재하지 않습니다.");
        }
        return cookies;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {
        Cookie[] cookies = getCookies(request);
        String token = cookieProvider.extractTokenFromCookie(cookies);

        Roles role = jwtProvider.getMemberInfo(token).roles();
        if (role != Roles.ADMIN) {
            throw new NotAdminException("관리자 권한이 없습니다.");
        }

        return true;
    }
}
