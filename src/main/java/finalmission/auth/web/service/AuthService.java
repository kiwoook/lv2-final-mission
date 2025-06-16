package finalmission.auth.web.service;


import finalmission.auth.web.controller.request.LoginRequest;
import finalmission.auth.dto.TokenDto;
import finalmission.auth.exception.InvalidAuthorizationException;
import finalmission.auth.provider.JwtProvider;
import finalmission.member.domain.Member;
import finalmission.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    public TokenDto login(LoginRequest loginRequest) {
        String email = loginRequest.email();
        String password = loginRequest.password();

        Member member = memberRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new InvalidAuthorizationException("이메일과 패스워드가 올바르지 않습니다."));

        String jwt = jwtProvider.generateJwt(member);

        return new TokenDto(jwt);
    }

    // 로그인 체크한다.

    // 로그아웃을 한다.

}
