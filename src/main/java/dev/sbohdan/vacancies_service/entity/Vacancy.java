package dev.sbohdan.vacancies_service.entity;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "vacancy")
public class Vacancy {
    @Id
    private Long id;
    private String slug;
    private String companyName;
    private String title;
    private String description;
    private boolean remote;
    private String url;
    private List<String> tags = new ArrayList<>();
    private List<String> jobTypes = new ArrayList<>();
    private String location;
    private long createdAt;

    public Vacancy(JsonNode jsonNode) {
        this.slug = jsonNode.get("slug").asText();
        this.companyName = jsonNode.get("company_name").asText();
        this.title = jsonNode.get("title").asText();
        this.description = jsonNode.get("description").asText();
        this.remote = jsonNode.get("remote").asBoolean();
        this.url = jsonNode.get("url").asText();
        jsonNode.get("tags").forEach(tag -> this.tags.add(tag.asText()));
        jsonNode.get("job_types").forEach(tag -> this.jobTypes.add(tag.asText()));
        this.location = jsonNode.get("location").asText();
        this.createdAt = jsonNode.get("created_at").asLong();
    }

}
