package dev.sbohdan.vacancies_service.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sbohdan.vacancies_service.entity.Vacancy;
import dev.sbohdan.vacancies_service.reposotory.VacancyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    private final VacancyRepository repository;
    private final WebClient webClient;

    @Scheduled(fixedDelay = 60 * 6000)
    public void updateVacancies() {
        final List<String> existedVacancies = repository
                .findVacanciesSlug()
                .collectList()
                .block();

        webClient.get()
                .exchangeToMono(response -> response.bodyToMono(JsonNode.class))
                .map(json -> json.get("data"))
                .flatMapMany(Flux::fromIterable)
                .map(Vacancy::fromJson)
                .filter(vacancy -> {
                    Objects.requireNonNull(existedVacancies);
                    return !existedVacancies.contains(vacancy.slug());
                })
                .flatMap(repository::save)
                .subscribe();
    }
}
