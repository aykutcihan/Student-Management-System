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
    @Query("select (count(s) > 0) from Student s")
    boolean findStudent();
    Student findByUsernameEquals(String username);
    @Query("select s from Student s where s.username = :username")
    Optional<Student> findByUsernameEqualsForOptional(String username);
    List<Student> findByMeetList_IdEquals(Long id);
    @Query("select s from Student s where s.ssn = :ssn")
    Student getStudentBySsn(String ssn);

    @Query("select s from Student s where s.username = ?1")
    Optional<Student> getStudentByUsernameForOptional(String username);

    boolean existsBySsn(String ssn);

    List<Student> getStudentByNameContaining(String name);

    @Query(value = "SELECT s FROM Student s WHERE s.id IN :studentIdList")
    Set<Student> getStudentsByStudentIdList(@Param("studentIdList") Set<Long> studentIdList);

    List<Student> getStudentByAdvisorTeacher_Id(Long advisorId);

    boolean existsByStudentNumber(int studentNumber);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);


    @Query(value = "SELECT s FROM Student s WHERE s.advisorTeacher.teacher.ssn = :ssn ")
    List<Student>  getStudentByAdvisorTeacher_Ssn(String ssn);

    @Query(value = "SELECT s FROM Student s WHERE s.id IN :id")
    List<Student> findByIdsEquals(Long[] id);
    Student findByIdEquals(Long id);


    boolean existsByIdEquals(Long studentId);
    @Query(value = "SELECT s FROM Student s WHERE s.advisorTeacher.teacher.username = :username ")
    List<Student> getStudentByAdvisorTeacher_Username(String username);

    boolean existsByUsername(String username);
    @Query(value = "SELECT s FROM Student s WHERE s.id = :id ")
    StudentResponse findByIdEqualsForStudentResponse(Long id);

    @Query(value = "SELECT MAX(s.studentNumber) FROM Student s ")
    int getMaxStudentNumber();
}
