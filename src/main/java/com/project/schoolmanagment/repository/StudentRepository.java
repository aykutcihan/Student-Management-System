package com.project.schoolmanagment.repository;

import com.project.schoolmanagment.entity.concretes.Student;
import com.project.schoolmanagment.payload.response.StudentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("select s from Student s where s.ssn = ?1")
    StudentResponse getStudentBySnn(String ssn);

    boolean existsBySsn(String ssn);

    List<Student> getStudentByNameContaining(String name);

    @Query(value = "SELECT s FROM Student s WHERE s.id IN :studentIdList")
    Set<Student> getStudentsByStudentIdList(@Param("studentIdList") Set<Long> studentIdList);

    List<Student> getStudentByAdvisorTeacher_Id(Long advisorId);

    boolean existsByStudentNumber(String studentNumber);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    Student getStudentBySsn(String ssn);
}
