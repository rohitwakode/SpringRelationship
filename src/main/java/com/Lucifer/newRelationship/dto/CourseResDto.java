package com.Lucifer.newRelationship.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CourseResDto(
        Integer id,

        String courseName,

        String duration,

        Double fees,

        String instructorName
) {
}
