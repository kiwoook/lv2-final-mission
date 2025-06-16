package finalmission.auth.dto;

import finalmission.member.domain.Roles;

public record MemberInfo(Long userId, Roles roles) {
}
