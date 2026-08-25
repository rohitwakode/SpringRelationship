package com.Lucifer.newRelationship.repo.custome;

import com.Lucifer.newRelationship.model.Student;

import java.util.List;

public interface StudentRepoCustom {

    List<Student> findByStudentCustom();
    List<Student> findStudentByDepartment(String departmentName);
}
