package pl.unityt.recruitment.github.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pl.unityt.recruitment.github.handlers.RestTemplateErrorHandler;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .errorHandler(new RestTemplateErrorHandler())
                .build();
    }
}