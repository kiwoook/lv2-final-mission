package finalmission.auth.utils;

import finalmission.auth.provider.JwtProvider;
import finalmission.member.domain.Email;
import finalmission.member.domain.Member;
import finalmission.member.domain.Password;
import finalmission.member.domain.Roles;
import finalmission.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenUtil {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private MemberRepository memberRepository;

    public String createAdminToken() {
        Email email = new Email("admin@email.com");
        Password password = new Password("1234");
        Member member = memberRepository.save(new Member(email, password, Roles.ADMIN));

        return jwtProvider.generateJwt(member);
    }

    public String createUserToken() {
        Email email = new Email("user@email.com");
        Password password = new Password("1234");
        Member member = memberRepository.save(new Member(email, password, Roles.USER));

        return jwtProvider.generateJwt(member);
    }

    public String createToken(Member member){
        return jwtProvider.generateJwt(member);
    }
}
