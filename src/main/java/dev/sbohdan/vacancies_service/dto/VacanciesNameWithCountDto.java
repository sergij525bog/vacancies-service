package dev.sbohdan.vacancies_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class VacanciesNameWithCountDto {
    private String name;
    private Long count;
}
