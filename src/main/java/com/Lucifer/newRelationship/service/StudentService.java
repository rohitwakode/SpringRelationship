package com.Lucifer.newRelationship.service;

import com.Lucifer.newRelationship.dto.AddResDto;
import com.Lucifer.newRelationship.dto.CourseResDto;
import com.Lucifer.newRelationship.dto.StdReqDto;
import com.Lucifer.newRelationship.dto.StdResDto;
import com.Lucifer.newRelationship.exception.DuplicateEmail;
import com.Lucifer.newRelationship.exception.ResourceNotFound;
import com.Lucifer.newRelationship.exception.StudentNotFound;
import com.Lucifer.newRelationship.model.Address;
import com.Lucifer.newRelationship.model.Course;
import com.Lucifer.newRelationship.model.Department;
import com.Lucifer.newRelationship.model.Student;
import com.Lucifer.newRelationship.projection.StudentProjection;
import com.Lucifer.newRelationship.projection.StudentProjectionDto;
import com.Lucifer.newRelationship.repo.CourseRepo;
import com.Lucifer.newRelationship.repo.DepartmentRepo;
import com.Lucifer.newRelationship.repo.StudentRepo;
import com.Lucifer.newRelationship.specifiation.StudentSpecification;
import jakarta.persistence.OptimisticLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private CourseRepo courseRepo;



      @Transactional  //save Student
    public StdResDto save(StdReqDto student) {
        Student existedStudent=studentRepo.findByEmail(student.email());
        if(existedStudent!=null){
           throw new DuplicateEmail("email already exists with: "+student.email());
        }

        //courseEntity
        List<Course>courses=courseRepo.findAllById(student.courseIds());

        //Address Entity
        Address address=new Address();
        address.setCity(student.address().city());
        address.setState(student.address().state());
        address.setCountry(student.address().country());

        //department
        Department department=departmentRepo.findById(student.deptId())
                .orElseThrow(()->new ResourceNotFound("department not found"));

        //Student Entity
        Student student1=new Student();
        student1.setName(student.name());
        student1.setEmail(student.email());
        student1.setPassword(student.password());
        student1.setAddress(address);
        student1.setDepartment(department);
        student1.setCourses(courses);
        Student student2=studentRepo.save(student1);
//        sendConfirmationEmail();
        return mapToResponse(student2);
    }
    private void sendConfirmationEmail() {
        throw new RuntimeException("something went wrong");
    }


    //update student
    public Student getStudentById(int id){
        return studentRepo.findById(id).orElseThrow(()->new StudentNotFound("student not found"));
    }

    public StdResDto update(StdReqDto student,Integer id) {
        Student existedStudent= getStudentById(id);

        if (existedStudent.getVersion().equals(student.version()) ) {
            throw new OptimisticLockException("student was already updated by another user");
        }

        if (student.name()!=null && !student.name().trim().isEmpty()){
            existedStudent.setName(student.name());}

        if (student.email()!=null  && !student.email().trim().isEmpty()) {
            existedStudent.setEmail(student.email());
        }
        if (student.password()!=null && !student.password().trim().isEmpty()) {
            existedStudent.setPassword(student.password());
        }

        //Address
        Address address=existedStudent.getAddress();
        if (address==null){
            address=new Address();
        }
        address.setCity(student.address().city());
        address.setState(student.address().state());
        address.setCountry(student.address().country());
        //department
        if (student.deptId() != null) {
            Department department=departmentRepo.findById(student.deptId())
                    .orElseThrow(()->new ResourceNotFound("department not found"));
            existedStudent.setDepartment(department);
        }
        Student updatedStudent= studentRepo.save(existedStudent);
        return mapToResponse(updatedStudent);
    }

    //find by id
    public StdResDto getById(Integer id) {
        Student student= studentRepo.findById(id).orElseThrow(
                () -> new StudentNotFound("student not found")
        );
        return mapToResponse(student);
    }


    //get All Students
    public List<StdResDto> getAllStudents() {
       return studentRepo.findAll()
               .stream()
               .map(this::mapToResponse)
               .toList();
    }


        //deleteStudent
        public void deleteById(Integer id) {
           Student student= studentRepo.findById(id).orElseThrow(
                    () -> new StudentNotFound("student not found"));
            student.setDeleted(true);
            studentRepo.save(student);
        }

    public void restoreDeleteById(Integer id) {
        Student student= studentRepo.findById(id).orElseThrow(
                () -> new StudentNotFound("student not found"));
        student.setDeleted(false);
        studentRepo.save(student);
    }

    //jpql methods
    public StdResDto findByName(String name) {
        Student student= studentRepo.findByName(name).orElseThrow(()->new StudentNotFound("student not found eith this name "+name));
        return mapToResponse(student);
    }
    public Long countByCourseName(String courseName){
        return studentRepo.countByCourses(courseName);
    }


    //helper method
    private StdResDto mapToResponse( Student student) {

        List<CourseResDto>course=student.getCourses()
                .stream()
                .map(course1 -> new CourseResDto(
                        course1.getId(),
                        course1.getCourseName(),
                        course1.getDuration(),
                        course1.getFees(),
                        course1.getInstructorName()
                ))
                .toList();

        return new StdResDto(
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
                course,
                student.getVersion()

        );
    }
    //-----------------sorting---------------------------
   public List<StdResDto> getAllStudentSortedByFirstName(){
        List<Student> students=studentRepo.findAll(Sort.by("name"));
    return students.stream()
            .map(this::mapToResponse)
            .toList();
    }

    //-------------projection-----------------------
    public List<StudentProjection>getStudentProjection(){
        return studentRepo.findBy();
    }

    //-------------projection Dto--------------------
    public List<StudentProjectionDto> getStudentProjectionDto(){
        return studentRepo.findAllProjectionDto();
    }

    //------------specification-----------------------
    public List<StdResDto>searchStudents(String name,String email,String course){
        Specification<Student> studentSpecification = Specification
                .where(StudentSpecification.hasName(name)
                        .and(StudentSpecification.hasEmail(email)
                                .and(StudentSpecification.hasCourse(course))));

        List<Student> students=studentRepo.findAll(studentSpecification);
        return  students.stream()
                .map(this::mapToResponse)
                .toList();
    }
    //------------------custome repo---------------------

    public List<StdResDto> getAllCustom(){
        return studentRepo.findByStudentCustom()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<StdResDto> getAllCustomDepartment(String departmentName){
        return studentRepo.findStudentByDepartment(departmentName)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    //------------query optimazation-----------------
    public List<StdResDto> queryOptimization(){
        return studentRepo.findAllWithCourses()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

}
