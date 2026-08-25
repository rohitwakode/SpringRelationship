package com.Lucifer.newRelationship.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CourseReqDto(

        @NotBlank(message = "course name is required")
        String courseName,

        @NotBlank(message = "duration is required")
        String  duration,

        @NotNull(message = "fees are required")
        Double fees,

        @NotBlank(message = "instructor name is required")
        String instructorName

) {
}
