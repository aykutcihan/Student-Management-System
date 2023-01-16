package com.project.schoolmanagment.repository;

import com.project.schoolmanagment.entity.concretes.Student;
import com.project.schoolmanagment.payload.response.StudentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("select s from Student s where s.ssn = :ssn")
    StudentResponse getStudentBySsn(String ssn);
    @Query("select s from Student s where s.ssn = ?1")
    Optional<Student> getStudentBySnnForOptional(String ssn);

    boolean existsBySsn(String ssn);

    List<Student> getStudentByNameContaining(String name);

    @Query(value = "SELECT s FROM Student s WHERE s.id IN :studentIdList")
    Set<Student> getStudentsByStudentIdList(@Param("studentIdList") Set<Long> studentIdList);

    List<Student> getStudentByAdvisorTeacher_Id(Long advisorId);

    boolean existsByStudentNumber(String studentNumber);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);


    @Query(value = "SELECT s FROM Student s WHERE s.advisorTeacher.teacher.ssn = :ssn ")
    List<Student>  getStudentByAdvisorTeacher_Ssn(String ssn);
}
