package com.Lucifer.newRelationship.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DeptReqDto(
        @NotBlank(message = "department name should not be blank")
        @Size(max = 60, message = "cant exceed 60 characters")
        String deptName
) {
}
