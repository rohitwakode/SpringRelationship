package com.Lucifer.newRelationship.service;

import com.Lucifer.newRelationship.dto.CourseReqDto;
import com.Lucifer.newRelationship.dto.CourseResDto;
import com.Lucifer.newRelationship.model.Course;
import com.Lucifer.newRelationship.repo.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepo courseRepo;

    public CourseResDto save(CourseReqDto courseDto) {
        Course course = new Course();
        course.setCourseName(courseDto.courseName());
        course.setDuration(courseDto.duration());
        course.setFees(courseDto.fees());
        course.setInstructorName(courseDto.instructorName());

        Course savedCourse = courseRepo.save(course);
        return mapToDto(savedCourse);
    }

    public List<CourseResDto>getAllCourses(){
        return courseRepo.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public CourseResDto getCourseById(Integer id){
        Course course = courseRepo.findById(id).orElseThrow(
                ()->new RuntimeException("course not found")
        );
        return mapToDto(course);
    }

    private CourseResDto mapToDto(Course course){
        return new CourseResDto(
                course.getId(),
                course.getCourseName(),
                course.getDuration(),
                course.getFees(),
                course.getInstructorName()
        );
    }
}
