package com.Lucifer.newRelationship.service;
import com.Lucifer.newRelationship.dto.*;
import com.Lucifer.newRelationship.exception.ResourceNotFound;
import com.Lucifer.newRelationship.model.Department;
import com.Lucifer.newRelationship.repo.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepo departmentRepo;

    public DeptResDto saveDepartment(DeptReqDto deptReqDto) {
        Department department = new Department();
        department.setDepartmentName(deptReqDto.deptName());

        Department savedDepartment = departmentRepo.save(department);
        return mapToResDto(savedDepartment);
    }

    public DeptResDto getDeptById(Integer id) {
       Department department= departmentRepo.findById(id).orElseThrow(
               ()-> new ResourceNotFound("Department not found")
       );
        return mapToResDto(department);
    }

    public List<DeptResDto> getAllDepartments() {
        return departmentRepo.findAll()
                .stream()
                .map(this::mapToResDto)
                .toList();
    }

    private DeptResDto mapToResDto(Department department) {

        List<StdResDto> students = department.getStudents()
                .stream()
                .map(student -> new StdResDto(
                        student.getId(),
                        student.getName(),
                        student.getEmail(),
                        student.getCreatedAt(),
                        student.getUpdatedAt(),
                        student.getCreatedBy(),
                        student.getUpdatedBy(),
                        new AddResDto(
                                student.getAddress().getId(),
                                student.getAddress().getCity(),
                                student.getAddress().getState(),
                                student.getAddress().getCountry()
                        ),
                        student.getDepartment().getDepartmentName(),
                        student.getCourses()
                                .stream()
                                .map(course -> new CourseResDto(
                                        course.getId(),
                                        course.getCourseName(),
                                        course.getDuration(),
                                        course.getFees(),
                                        course.getInstructorName()
                                ))
                                .toList(),
                        student.getVersion()
                ))
                .toList();

        return new DeptResDto(
                department.getId(),
                department.getDepartmentName(),
                students
        );
    }


}
