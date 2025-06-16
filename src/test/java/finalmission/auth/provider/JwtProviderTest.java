package finalmission.auth.provider;

import finalmission.auth.dto.MemberInfo;
import finalmission.fixture.db.MemberDbFixture;
import finalmission.member.domain.Member;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;


@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class JwtProviderTest {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private MemberDbFixture memberDbFixture;

    @DisplayName("토큰을 파싱할 수 있다.")
    @Test
    void getMemberInfoTest() {
        // given
        Member user = memberDbFixture.createUser();
        String jwt = jwtProvider.generateJwt(user);

        // when
        MemberInfo memberInfo = jwtProvider.getMemberInfo(jwt);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(memberInfo.userId()).isEqualTo(user.getId());
            softly.assertThat(memberInfo.roles()).isEqualTo(user.getRole());
        });
    }

}