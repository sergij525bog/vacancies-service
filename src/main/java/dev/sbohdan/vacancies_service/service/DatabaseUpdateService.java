package dev.sbohdan.vacancies_service.service;

import com.fasterxml.jackson.databind.JsonNode;
import dev.sbohdan.vacancies_service.entity.Vacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DatabaseUpdateService {
    private final R2dbcEntityTemplate template;

    @Scheduled(fixedDelay = 60_000)
    public void updateVacancies() {
        final List<String> existedVacancies = template
                .select(Vacancy.class).all()
                .map(Vacancy::getSlug)
                .collectList()
                .block();

        WebClient.create("https://www.arbeitnow.com/api/job-board-api")
                .mutate()
                .codecs(config -> config.defaultCodecs().maxInMemorySize(10_000_000)).build()
                .get()
                .exchangeToMono(response -> response.bodyToMono(JsonNode.class))
                .map(json -> json.get("data"))
                .flatMapMany(Flux::fromIterable)
                .map(Vacancy::new)
                .filter(vacancy -> {
                    Objects.requireNonNull(existedVacancies);
                    return !existedVacancies.contains(vacancy.getSlug());
                })
                .flatMap(template::insert)
                .subscribe();
    }
}
