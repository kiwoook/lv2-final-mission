package finalmission.holiday.client;


import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface HolidayClient {

    @GetExchange("/getRestDeInfo")
    String getHolidayInfo(@RequestParam(name = "serviceKey") String serviceKey,
                          @RequestParam(name = "solYear") int solYear,
                          @RequestParam(name = "solMonth") int solMonth,
                          @RequestParam(name = "_type") String type);

}
