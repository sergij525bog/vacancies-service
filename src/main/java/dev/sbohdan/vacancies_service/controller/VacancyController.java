package dev.sbohdan.vacancies_service.controller;

import dev.sbohdan.vacancies_service.dto.CityVacanciesCountDto;
import dev.sbohdan.vacancies_service.dto.VacanciesNameWithCountDto;
import dev.sbohdan.vacancies_service.dto.VacancyDto;
import dev.sbohdan.vacancies_service.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/vacancies")
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService service;

    @GetMapping()
    public Flux<VacancyDto> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "desc") String order) {
        return service.findAllPageable(page, size, sort, order);
    }

    @GetMapping("/statistics")
    public Flux<CityVacanciesCountDto> getVacanciesStatistics() {
        return service.getVacancyStatistics();
    }

    @GetMapping("/popular")
    public Flux<VacanciesNameWithCountDto> getPopularVacancies() {
        return service.getPopularVacancies();
    }
}
