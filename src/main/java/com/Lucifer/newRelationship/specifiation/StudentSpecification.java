package com.Lucifer.newRelationship.specifiation;

//this class will contain all filtering logic

import com.Lucifer.newRelationship.model.Student;
import org.springframework.data.jpa.domain.Specification;

public class StudentSpecification {

    public static Specification<Student> hasName(String name ) {
        if (name == null||name.isBlank()) {
            return null;
        }
        return (root, criteriaQuery, criteriaBuilder) ->
        criteriaBuilder.equal(root.get("name"), name);
    }

    public static Specification<Student> hasEmail(String email) {
        if (email == null||email.isBlank()) {
            return null;
        }
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("email"), email);
    }

    public static Specification<Student> hasCourse(String courses) {
        if (courses  == null||courses.isBlank()) {
            return null;
        }
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("courses"), courses);
    }
}
