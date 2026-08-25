package com.Lucifer.newRelationship.dto;

import java.time.LocalDateTime;
import java.util.List;

public record StdResDto(
        Integer id,
        String name,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy,
        AddResDto address,
        String departmentName,
        List<CourseResDto>courses,
        Long version

) {
}
