package finalmission.holiday.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class HolidayServiceTest {

    @Autowired
    private HolidayService holidayService;

    private static Stream<Arguments> holidayTestArguments() {
        return Stream.of(
                Arguments.of(LocalDate.of(2025, 12, 25), true),
                Arguments.of(LocalDate.of(2025, 12, 26), false),
                Arguments.of(LocalDate.of(2025, 2, 2), false)
        );
    }

    @DisplayName("휴일인지를 검증한다.")
    @ParameterizedTest
    @MethodSource("holidayTestArguments")
    void holidayTest1(LocalDate localDate, boolean expected) {
        // given
        // when
        boolean result = holidayService.isHoliday(localDate);

        // then
        assertThat(result).isEqualTo(expected);
    }
}