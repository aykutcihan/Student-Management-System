package com.project.schoolmanagment.repository;

import com.project.schoolmanagment.entity.concretes.StudentInfo;
import com.project.schoolmanagment.payload.response.StudentInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentInfoRepository extends JpaRepository<StudentInfo, Long> {
    @Query("select (count(s) > 0) from StudentInfo s where s.student.id = ?1")
    boolean existsByStudent_IdEquals(Long id);
    @Query("select count(s) from StudentInfo s where s.student.id = ?1")
    long countByStudent_IdEquals(Long id);
    @Query("select s from StudentInfo s where s.student.id = ?1")
    List<StudentInfo> findByStudent_IdEquals(Long id);
    StudentInfo findByIdEquals(Long id);
    boolean existsByIdEquals(Long id);

    List<StudentInfo> getAllByStudentId_Id(Long studentId_id);


    //@Query("select s from StudentInfo s where s.studentId.ssn = ?2")
    Page<StudentInfo> findByStudentId_SsnEquals(Pageable pageable, String ssn);

    @Query("select s from StudentInfo s ")
    Page<StudentInfo> getAll(Pageable pageable);

    @Query("select s from StudentInfo s where s.teacher.ssn = ?1")
    Page<StudentInfo> findByTeacherId_SsnEquals(String ssn, Pageable pageable);

    @Query("select s from StudentInfo s where s.teacher.username = ?1")
    Page<StudentInfo> findByTeacherId_UsernameEquals(String username, Pageable pageable);


    @Query("select s from StudentInfo s where s.student.username = ?1")
    Page<StudentInfo> findByStudentId_UsernameEquals(String username, Pageable pageable);


}