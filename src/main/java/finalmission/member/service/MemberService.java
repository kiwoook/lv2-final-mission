package finalmission.member.service;

import finalmission.member.domain.Email;
import finalmission.member.domain.Member;
import finalmission.member.domain.Password;
import finalmission.member.dto.request.MemberCreateRequest;
import finalmission.member.dto.response.MemberInfoResponse;
import finalmission.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberInfoResponse signUp(MemberCreateRequest createRequest) {
        Email email = new Email(createRequest.email());
        Password password = new Password(createRequest.password());

        Member member = new Member(email, password);
        Member saved = memberRepository.save(member);

        return MemberInfoResponse.of(saved);
    }
}
