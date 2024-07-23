package dev.sbohdan.vacancies_service.entity;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;

@Builder
@Table(name = "vacancy")
public record Vacancy(
        @Id
        Long id,
        String slug,
        String companyName,
        String title,
        String description,
        boolean remote,
        String url,
        List<String> tags,
        List<String> jobTypes,
        String location,
        long createdAt
) {

    public static Vacancy fromJson(JsonNode jsonNode) {
        return Vacancy.builder()
                .slug(jsonNode.get("slug").asText())
                .companyName(jsonNode.get("company_name").asText())
                .title(jsonNode.get("title").asText())
                .description(jsonNode.get("description").asText())
                .remote(jsonNode.get("remote").asBoolean())
                .url(jsonNode.get("url").asText())
                .tags(getListFromJson(jsonNode, "tags"))
                .jobTypes(getListFromJson(jsonNode, "job_types"))
                .location(jsonNode.get("location").asText())
                .createdAt(jsonNode.get("created_at").asLong())
                .build();
    }

    private static List<String> getListFromJson(JsonNode jsonNode, String fieldName) {
        final List<String> list = new ArrayList<>();

        jsonNode.get(fieldName)
                .forEach(field -> list.add(field.asText()));

        return list;
    }
}
