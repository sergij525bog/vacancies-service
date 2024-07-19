package dev.sbohdan.vacancies_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CityVacanciesCountDto {
    private String city;
    private Long count;
}
