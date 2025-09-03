package finalmission.holiday.client;

import static org.assertj.core.api.Assertions.assertThat;

import finalmission.holiday.infra.client.HolidayClient;
import finalmission.holiday.infra.dto.HolidayCountResponse;
import finalmission.holiday.infra.dto.HolidayInfoResponse;
import finalmission.holiday.infra.dto.HolidayInfosResponse;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class HolidayClientTest {

    private static final String JSON_TYPE = "json";
    private static final String SERVICE_KEY = "sJ+Lz9TtILnR3+qcTUp+uGxm2QJiToGjZPFUv027hv61eVXQpnLjPINKXAfU+3JdtNcW/GrfjMUieJTCtLiJJg==";

    @Autowired
    private HolidayClient holidayClient;

    private static Stream<Arguments> totalCountTestArguments() {
        return Stream.of(
                Arguments.of(2025, 12, 1),
                Arguments.of(2025, 10, 6)
        );
    }

    @DisplayName("쉬는 날이 하나인 경우")
    @Test
    void test1() {
        HolidayInfoResponse result = holidayClient.getHolidayInfo(SERVICE_KEY, 2025, 12, JSON_TYPE);

        assertThat(result).isNotNull();
    }

    @DisplayName("쉬는 날이 여러 개인 경우 경우")
    @Test
    void test2() {
        HolidayInfosResponse result = holidayClient.getHolidayInfos(SERVICE_KEY, 2025, 10, JSON_TYPE);

        assertThat(result).isNotNull();
    }

    @ParameterizedTest
    @DisplayName("쉬는 날짜를 확인할 수 있다.")
    @MethodSource("totalCountTestArguments")
    void totalCountTest1(int year, int month, int expected) {
        // given
        // when
        HolidayCountResponse result = holidayClient.getHolidayTotalCount(SERVICE_KEY, year, month, JSON_TYPE);

        // then
        assertThat(result.response().body().totalCount()).isEqualTo(expected);
    }

}