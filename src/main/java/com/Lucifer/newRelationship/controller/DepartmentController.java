package com.Lucifer.newRelationship.controller;

import com.Lucifer.newRelationship.dto.DeptReqDto;
import com.Lucifer.newRelationship.dto.DeptResDto;
import com.Lucifer.newRelationship.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dept")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<DeptResDto>>getAllDept(){
        return new ResponseEntity<>(departmentService.getAllDepartments(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeptResDto> getById(@PathVariable Integer id){
        return new ResponseEntity<>(departmentService.getDeptById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DeptResDto> saved(@Valid @RequestBody DeptReqDto dept){
        return new ResponseEntity<>(departmentService.saveDepartment(dept),HttpStatus.ACCEPTED);
    }
}
