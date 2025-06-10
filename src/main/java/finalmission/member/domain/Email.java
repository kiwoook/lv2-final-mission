package finalmission.member.domain;

import finalmission.common.exception.InvalidArgumentException;
import jakarta.persistence.Embeddable;

@Embeddable
public record Email(String email) {

    private static final String EMAIL_REGEX_PATTERN = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";

    public Email(String email) {
        this.email = email;
        validate(email);
    }

    public void validate(String email) {
        if (email == null) {
            throw new InvalidArgumentException("이메일은 존재해야 합니다.");
        }

        if (!email.matches(EMAIL_REGEX_PATTERN)) {
            throw new InvalidArgumentException("이메일 형식이 아닙니다!");
        }
    }
}
