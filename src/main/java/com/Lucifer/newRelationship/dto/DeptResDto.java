package com.Lucifer.newRelationship.dto;

import com.Lucifer.newRelationship.model.Student;

import java.util.List;

public record DeptResDto(
        Integer id,
        String departmentName,
        List<StdResDto> students
) {
}
