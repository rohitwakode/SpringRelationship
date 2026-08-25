package com.Lucifer.newRelationship.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String courseName;

    private String duration;

    private Double fees;

    private String instructorName;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students=new ArrayList<>();



}
