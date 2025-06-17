package finalmission.holiday.infra.dto;

public record HolidayCountResponse(Response response) {

    public record Response(
            Body body
    ) {
    }

    public record Body(
            int totalCount
    ) {
    }
}
