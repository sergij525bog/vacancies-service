package dev.sbohdan.vacancies_service.reposotory;

import dev.sbohdan.vacancies_service.entity.Vacancy;
import dev.sbohdan.vacancies_service.dto.CityVacanciesCountDto;
import dev.sbohdan.vacancies_service.dto.VacanciesNameWithCountDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface VacancyRepository extends ReactiveCrudRepository<Vacancy, Long> {
    @Query("""
                select v.location as city, count(v.id) as count
                from Vacancy as v
                group by v.location order by v.location""")
    Flux<CityVacanciesCountDto> findLocationAndVacancyCount();

    @Query("""
                select v.title as name, count(v.id) as count
                from Vacancy v
                group by v.title
                order by count desc
                limit 10""")
    Flux<VacanciesNameWithCountDto> findMostPopularVacations();

    @Query("select v.slug as slug from Vacancy v")
    Flux<String> findVacanciesSlug();

    Flux<Vacancy> findAllBy(Pageable pageable);

}
