package dev.sbohdan.vacancies_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class VacancyDto {
private String company_name;
    private String title;
    private String description;
    private boolean remote;
    private String url;
    private List<String> tags;
    private List<String> job_types;
    private String location;
    private LocalDateTime created_at;
}
