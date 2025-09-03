package finalmission.holiday.infra.dto;

import java.util.List;

public record HolidayInfosResponse(Response response) {

    public record Response(
            Body body
    ) {
    }

    public record Body(
            Items items,
            int numOfRows,
            int pageNo,
            int totalCount
    ) {
    }

    public record Items(
            List<Item> item
    ) {
    }

    public record Item(
            String dateKind,
            String dateName,
            String isHoliday,
            int locdate,
            int seq
    ) {
    }
}
