package finalmission.common.config;

import finalmission.holiday.infra.client.HolidayClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpInterfaceConfig {

    private static final String HOLIDAY_SERVICE_URL = "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService";

    @Bean
    public HolidayClient holidayClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl(HOLIDAY_SERVICE_URL)
                .build();

        RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);

        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();

        return httpServiceProxyFactory.createClient(HolidayClient.class);
    }

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl(HOLIDAY_SERVICE_URL)
                .build();
    }

}
