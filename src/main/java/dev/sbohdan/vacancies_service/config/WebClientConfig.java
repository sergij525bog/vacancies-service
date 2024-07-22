package dev.sbohdan.vacancies_service.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    @Value("${config.json.max-in-memory-size}")
    private Integer maxInMemorySize;

    @Value("${config.api.url}")
    private String url;

    @Bean
    public WebClient webClient() {
        return WebClient.create(url)
                .mutate()
                .codecs(config -> config
                        .defaultCodecs()
                        .maxInMemorySize(maxInMemorySize))
                .build();
    }
}
