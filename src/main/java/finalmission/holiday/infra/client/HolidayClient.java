package finalmission.holiday.infra.client;


import finalmission.holiday.infra.dto.HolidayCountResponse;
import finalmission.holiday.infra.dto.HolidayInfoResponse;
import finalmission.holiday.infra.dto.HolidayInfosResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface HolidayClient {

    @GetExchange("/getRestDeInfo")
    HolidayCountResponse getHolidayTotalCount(@RequestParam(name = "serviceKey") String serviceKey,
                                              @RequestParam(name = "solYear") int solYear,
                                              @RequestParam(name = "solMonth") int solMonth,
                                              @RequestParam(name = "_type") String type);

    @GetExchange("/getRestDeInfo")
    HolidayInfoResponse getHolidayInfo(@RequestParam(name = "serviceKey") String serviceKey,
                                       @RequestParam(name = "solYear") int solYear,
                                       @RequestParam(name = "solMonth") int solMonth,
                                       @RequestParam(name = "_type") String type);

    @GetExchange("/getRestDeInfo")
    HolidayInfosResponse getHolidayInfos(@RequestParam(name = "serviceKey") String serviceKey,
                                         @RequestParam(name = "solYear") int solYear,
                                         @RequestParam(name = "solMonth") int solMonth,
                                         @RequestParam(name = "_type") String type);

}
