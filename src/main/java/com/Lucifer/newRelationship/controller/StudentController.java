package com.Lucifer.newRelationship.controller;

import com.Lucifer.newRelationship.dto.StdReqDto;
import com.Lucifer.newRelationship.dto.StdResDto;
import com.Lucifer.newRelationship.model.Student;
import com.Lucifer.newRelationship.projection.StudentProjection;
import com.Lucifer.newRelationship.projection.StudentProjectionDto;
import com.Lucifer.newRelationship.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/std")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<List<StdResDto>>getAllStudents(){
        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StdResDto> getStudentById(@PathVariable Integer id){
        return new ResponseEntity<>(studentService.getById(id),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StdResDto> addStudent(@Valid @RequestBody StdReqDto stdReqDto){
        return new ResponseEntity<>(studentService.save(stdReqDto),HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StdResDto> updateStudent(@PathVariable Integer id, @RequestBody StdReqDto stdReqDto){
        return new ResponseEntity<>(studentService.update(stdReqDto,id),HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Integer id){
        studentService.deleteById(id);
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/restore/{id}")
    public ResponseEntity<Void> RestoreDeleteStudent(@PathVariable Integer id){
       studentService.restoreDeleteById(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


    @GetMapping("/name")
    public ResponseEntity<StdResDto> getAllStudentsByName(@RequestParam String name){
        return new ResponseEntity<>(studentService.findByName(name),HttpStatus.OK);
    }

    @GetMapping("/sort")
    public ResponseEntity<List<StdResDto>> getAllStudentsByName(){
        return  new ResponseEntity<>(studentService.getAllStudentSortedByFirstName() ,HttpStatus.OK);
    }

    @GetMapping("/count")
    public Long getStudentBuCountByCourse(@RequestParam String courseName){
        Long students=studentService.countByCourseName(courseName);
        return students;
    }


    @GetMapping("/projection")
    public ResponseEntity<List<StudentProjection>> getAllstudentprojection(){
        return new ResponseEntity<>(studentService.getStudentProjection(),HttpStatus.OK);
    }

    @GetMapping("/projectionDto")
    public ResponseEntity<List<StudentProjectionDto>> getAllStudentProjectionDto(){
        return new ResponseEntity<>(studentService.getStudentProjectionDto(),HttpStatus.OK);
    }

    //--------------specification--------------
    @GetMapping("/search")
    public ResponseEntity<List<StdResDto>> AllStudentsBySearch(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String course){

        List<StdResDto>students=studentService.searchStudents(name,email,course);
        return new ResponseEntity<>(students,HttpStatus.OK);
    }

    //--------from custome repo------------
    @GetMapping("/custom")
    public ResponseEntity<List<StdResDto>> getAllStudentsByCustom(){
        return new ResponseEntity<>(studentService.getAllCustom(),HttpStatus.OK);
    }


    @GetMapping("/custom/dpt")
    public ResponseEntity<List<StdResDto>> getAllStudentsByCustomDepartment(@RequestParam String departmentName){
        return new ResponseEntity<>(studentService.getAllCustomDepartment(departmentName),HttpStatus.OK);
    }


    @GetMapping("/optimize")
    public ResponseEntity<List<StdResDto>> optimize(){
        return new ResponseEntity<>(studentService.queryOptimization(),HttpStatus.OK);
    }

}
