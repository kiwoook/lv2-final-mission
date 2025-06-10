package finalmission.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record MemberCreateRequest(@Email @NotEmpty String email,
                                  @NotEmpty String password) {
}
