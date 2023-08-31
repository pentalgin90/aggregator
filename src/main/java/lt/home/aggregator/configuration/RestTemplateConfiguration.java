package lt.home.aggregator.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateConfiguration {

    @Bean
    public RestTemplate webClient(RestTemplateBuilder builder) {
        return builder.build();
    }
}
