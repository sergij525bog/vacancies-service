package dev.sbohdan.vacancies_service.service;

import dev.sbohdan.vacancies_service.entity.Vacancy;
import dev.sbohdan.vacancies_service.dto.CityVacanciesCountDto;
import dev.sbohdan.vacancies_service.dto.VacanciesNameWithCountDto;
import dev.sbohdan.vacancies_service.reposotory.VacancyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class VacancyService {
    private final VacancyRepository repository;

    public Flux<Vacancy> findAllPageable(int page, int size, String sort, String order) {
        final PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.fromString(order), sort));
        return repository.findAllBy(pageRequest);
    }

    public Flux<CityVacanciesCountDto> getVacancyStatistics() {
        return repository.findLocationAndVacancyCount();
    }

    public Flux<VacanciesNameWithCountDto> getPopularVacancies() {
        return repository.findMostPopularVacations();
    }
}
