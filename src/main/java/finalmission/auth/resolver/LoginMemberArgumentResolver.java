package finalmission.auth.resolver;

import finalmission.auth.exception.TokenNotFoundException;
import finalmission.auth.provider.CookieProvider;
import finalmission.auth.provider.JwtProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


@RequiredArgsConstructor
@Component
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtProvider jwtProvider;
    private final CookieProvider cookieProvider;

    private static Cookie[] getCookies(HttpServletRequest request) {
        if (request == null) {
            throw new IllegalArgumentException();
        }
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new TokenNotFoundException("토큰이 존재하지 않습니다.");
        }
        return cookies;
    }

    // 파라미터 사용법 공부
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Authenticated.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        Cookie[] cookies = getCookies(request);

        String token = cookieProvider.extractTokenFromCookie(cookies);
        if (token == null || token.isBlank()) {
            throw new TokenNotFoundException("토큰이 존재하지 않습니다.");
        }

        return jwtProvider.getMemberInfo(token);
    }
}
