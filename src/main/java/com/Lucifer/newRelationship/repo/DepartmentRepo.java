package com.Lucifer.newRelationship.repo;

import com.Lucifer.newRelationship.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Integer> {

}