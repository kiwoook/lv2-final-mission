package finalmission.holiday.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class HolidayClientTest {

    @Autowired
    private HolidayClient holidayClient;

    @Value("${holiday.serviceKey}")
    private String serviceKey;

    @Test
    void test1() {
        String holidayInfo = holidayClient.getHolidayInfo(serviceKey, 2025, 12, "json");
    }

}