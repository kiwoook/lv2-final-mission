package finalmission.holiday.infra.dto;

public record HolidayInfoResponse(Response response) {

    public record Response(
            Body body
    ) {
    }

    public record Body(
            Items items
    ) {
    }

    public record Items(
            Item item
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
