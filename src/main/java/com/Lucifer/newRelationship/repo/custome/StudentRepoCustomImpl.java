package com.Lucifer.newRelationship.repo.custome;

import com.Lucifer.newRelationship.model.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class StudentRepoCustomImpl implements StudentRepoCustom {

    @PersistenceContext
    private EntityManager em;


    @Override
    public List<Student> findByStudentCustom() {

        TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s", Student.class);
        return query.getResultList();
    }

    @Override
    public List<Student> findStudentByDepartment(String departmentName) {
        TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s WHERE s.department.departmentName=:departmentName", Student.class);
        query.setParameter("departmentName", departmentName);
        return query.getResultList();
    }
}
