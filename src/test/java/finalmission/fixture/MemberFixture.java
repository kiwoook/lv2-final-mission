package finalmission.fixture;

import finalmission.member.domain.Email;
import finalmission.member.domain.Member;
import finalmission.member.domain.Password;
import finalmission.member.domain.Roles;

public final class MemberFixture {

    private MemberFixture() {
    }

    public static Member createUser() {
        Email email = new Email("user1@naver.com");
        Password password = new Password("1234");

        return new Member(email, password, Roles.USER);
    }
}
