package finalmission.holiday.service;


import finalmission.holiday.infra.parser.HolidayParser;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HolidayService {

    private final HolidayParser holidayParser;

    public boolean isHoliday(LocalDate localDate) {
        List<LocalDate> holiday = holidayParser.getHoliday(localDate);

        return holiday.contains(localDate);
    }
}
