package finalmission.holiday.infra.parser;

import finalmission.holiday.infra.client.HolidayClient;
import finalmission.holiday.infra.dto.HolidayCountResponse;
import finalmission.holiday.infra.dto.HolidayInfoResponse;
import finalmission.holiday.infra.dto.HolidayInfosResponse;
import finalmission.holiday.infra.dto.HolidayInfosResponse.Item;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HolidayParser {

    private static final String JSON_TYPE = "json";

    private final String secretKey;
    private final HolidayClient holidayClient;

    public HolidayParser(@Value("${holiday-api.service-key}") String secretKey, HolidayClient holidayClient) {
        this.secretKey = secretKey;
        this.holidayClient = holidayClient;
    }

    public List<LocalDate> getHoliday(LocalDate localDate) {
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        int count = getHolidayCount(year, month);

        return getLocalDates(count, year, month);
    }

    private List<LocalDate> getLocalDates(int count, int year, int month) {
        if (count == 0) {
            return Collections.emptyList();
        }

        if (count == 1) {
            return getLocalDate(year, month);
        }

        HolidayInfosResponse holidayInfos = holidayClient.getHolidayInfos(secretKey, year, month, JSON_TYPE);

        return getLocalDates(holidayInfos);
    }

    private List<LocalDate> getLocalDate(int year, int month) {
        HolidayInfoResponse holidayInfo = holidayClient.getHolidayInfo(secretKey, year, month, JSON_TYPE);
        LocalDate parsed = parseLocalDate(holidayInfo.response().body().items().item().locdate());

        return List.of(parsed);
    }

    private List<LocalDate> getLocalDates(HolidayInfosResponse holidayInfos) {
        return holidayInfos.response()
                .body()
                .items()
                .item()
                .stream()
                .map(Item::locdate)
                .map(this::parseLocalDate)
                .toList();
    }

    private LocalDate parseLocalDate(int localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        return LocalDate.parse(String.valueOf(localDate), formatter);
    }

    private int getHolidayCount(int year, int month) {
        HolidayCountResponse holidayTotalCount = holidayClient.getHolidayTotalCount(secretKey, year, month, JSON_TYPE);

        return holidayTotalCount.response().body().totalCount();
    }
}
