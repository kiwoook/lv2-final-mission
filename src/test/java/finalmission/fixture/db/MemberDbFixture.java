package finalmission.fixture.db;

import finalmission.fixture.MemberFixture;
import finalmission.member.domain.Member;
import finalmission.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberDbFixture {

    @Autowired
    private MemberRepository memberRepository;

    public Member createUser() {
        Member user = MemberFixture.createUser();

        return memberRepository.save(user);
    }

}
