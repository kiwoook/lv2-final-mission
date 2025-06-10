package finalmission.member.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import finalmission.common.exception.InvalidArgumentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class EmailTest {

    @DisplayName("이메일 형식이 아니면 예외가 발생한다")
    @NullAndEmptySource
    @ValueSource(strings = {"easd1312", "@naver.com", "x"})
    @ParameterizedTest
    void email_regex_throwsException(String value) {
        // given

        // when & then
        assertThatThrownBy(() -> {
            new Email(value);
        }).isInstanceOf(InvalidArgumentException.class);
    }

    @DisplayName("이메일 생성 테스트")
    @Test
    void emailTest1() {
        // given
        String email = "ads312@naver.com";
        // when

        // then
        assertThatCode(() -> new Email(email))
                .doesNotThrowAnyException();
    }

}