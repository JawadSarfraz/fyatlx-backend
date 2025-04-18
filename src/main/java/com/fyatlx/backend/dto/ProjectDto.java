package com.fyatlx.backend.dto;

import com.fyatlx.backend.entity.Project;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ProjectDto {

    private String title;
    private String description;
    private String estimatedBudget;
    private LocalDate deadline;

    public static ProjectDto from(Project project) {
        return ProjectDto.builder()
                .title(project.getTitle())
                .description(project.getDescription())
                .estimatedBudget(project.getEstimatedBudget())
                .deadline(project.getDeadline())
                .build();
    }
}