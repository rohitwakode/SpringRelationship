package com.Lucifer.newRelationship.controller;

import com.Lucifer.newRelationship.dto.CourseReqDto;
import com.Lucifer.newRelationship.dto.CourseResDto;
import com.Lucifer.newRelationship.model.Course;
import com.Lucifer.newRelationship.repo.CourseRepo;
import com.Lucifer.newRelationship.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseResDto>> getAllCourse() {
        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResDto> getById(@PathVariable Integer id) {
        return new ResponseEntity<>(courseService.getCourseById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CourseResDto> saveCourse(@RequestBody CourseReqDto courseReDto) {
        return new ResponseEntity<>(courseService.save(courseReDto),HttpStatus.CREATED);
    }

}
