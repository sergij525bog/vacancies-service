package dev.sbohdan.vacancies_service.reposotory;

import dev.sbohdan.vacancies_service.dto.CityVacanciesCountDto;
import dev.sbohdan.vacancies_service.dto.VacanciesNameWithCountDto;
import dev.sbohdan.vacancies_service.dto.VacancyDto;
import dev.sbohdan.vacancies_service.entity.Vacancy;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface VacancyRepository extends ReactiveCrudRepository<Vacancy, Long> {
    @Query("""
                select v.location city, count(v.id) count
                from Vacancy v
                group by v.location
                order by v.location""")
    Flux<CityVacanciesCountDto> findLocationAndVacancyCount();

    @Query("""
                select v.title name, count(v.id) count
                from Vacancy v
                group by v.title
                order by count desc
                limit 10""")
    Flux<VacanciesNameWithCountDto> findMostPopularVacations();

    @Query("select v.slug as slug from Vacancy v")
    Flux<String> findVacanciesSlug();

    @Query("""
                select v.company_name, v.title, v.description,
                v.remote, v.url, v.tags, v.job_types, v.location,
                v.created_at
                from Vacancy v""")
    Flux<VacancyDto> findAllAsDto(Pageable pageable);
}
