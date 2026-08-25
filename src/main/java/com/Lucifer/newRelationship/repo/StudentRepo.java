package com.Lucifer.newRelationship.repo;

import com.Lucifer.newRelationship.model.Student;
import com.Lucifer.newRelationship.projection.StudentProjection;
import com.Lucifer.newRelationship.projection.StudentProjectionDto;
import com.Lucifer.newRelationship.repo.custome.StudentRepoCustom;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer>,
        JpaSpecificationExecutor<Student>, StudentRepoCustom {

    //------------derived query----------------
    Student findByEmail(String email);
    Optional<Student> findByName(String name);



    //---------jpql= Java Persistence Query Language-------------------------------
    @Query("""
    SELECT COUNT(s)FROM Student s JOIN s.courses c WHERE LOWER(c.courseName)= LOWER(:courseName)
    """)
    long countByCourses(@Param("courseName") String courseName);


    //------------interface projection---------
    List<StudentProjection>findBy();

    //-------------Dto Projection--------------
    @Query("""
              SELECT new com.Lucifer.newRelationship.projection.StudentProjectionDto(
                            s.id,s.name,s.email
                            )FROM Student s""")
    List<StudentProjectionDto> findAllProjectionDto();

    //------------------query optimization-------------
//    @Query("""
//SELECT DISTINCT s
//FROM Student s
//LEFT JOIN FETCH s.courses
//LEFT JOIN FETCH s.department
//LEFT JOIN FETCH s.address
//""")
//    List<Student>findAllWithCourses();

    //------------second way of query optimization-------------


    @EntityGraph(attributePaths = {"address","department","courses"})
    @Query("SELECT s FROM Student s")
    List<Student>findAllWithCourses();

}