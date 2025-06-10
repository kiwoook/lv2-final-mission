package finalmission.member.domain;

import finalmission.common.exception.InvalidArgumentException;
import jakarta.persistence.Embeddable;

@Embeddable
public record Password(String password) {

    private static final int MAX_PASSWORD_LENGTH = 25;

    public Password(String password) {
        this.password = password;
        validate();
    }

    public void validate() {
        if (password == null) {
            throw new InvalidArgumentException("패스워드는 존재해야 합니다.");
        }

        if (password.length() > MAX_PASSWORD_LENGTH) {
            throw new InvalidArgumentException("패스워드는 " + MAX_PASSWORD_LENGTH + "자 이하여야 합니다");
        }
    }
}
