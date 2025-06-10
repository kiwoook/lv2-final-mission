package finalmission.member.dto.response;

import finalmission.member.domain.Member;

public record MemberInfoResponse(Long id, String email) {

    public static MemberInfoResponse of(Member member) {
        return new MemberInfoResponse(member.getId(), member.getEmail().email());
    }
}
