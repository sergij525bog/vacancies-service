package dev.sbohdan.vacancies_service.service;

import dev.sbohdan.vacancies_service.dto.CityVacanciesCountDto;
import dev.sbohdan.vacancies_service.dto.VacanciesNameWithCountDto;
import dev.sbohdan.vacancies_service.dto.VacancyDto;
import dev.sbohdan.vacancies_service.reposotory.VacancyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class VacancyService {
    private final Set<String> sortableFields = Set.of("createdAt", "location", "title", "companyName");
    private final VacancyRepository repository;

    public Flux<VacancyDto> findAllPageable(int page, int size, String sort, String order) {
        final String sortBy = sortableFields.contains(sort)
                ? sort
                : "createdAt";

        final PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.fromString(order), sortBy));
        return repository.findAllAsDto(pageRequest);
    }

    public Flux<CityVacanciesCountDto> getVacancyStatistics() {
        return repository.findLocationAndVacancyCount();
    }

    public Flux<VacanciesNameWithCountDto> getPopularVacancies() {
        return repository.findMostPopularVacations();
    }
}
