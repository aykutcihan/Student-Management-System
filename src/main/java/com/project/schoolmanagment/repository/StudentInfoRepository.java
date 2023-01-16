package com.project.schoolmanagment.repository;

import com.project.schoolmanagment.entity.concretes.StudentInfo;
import com.project.schoolmanagment.payload.response.StudentInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentInfoRepository extends JpaRepository<StudentInfo, Long> {

    List<StudentInfo> getAllByStudentId_Id(Long studentId_id);


    //@Query("select s from StudentInfo s where s.studentId.ssn = ?2")
    Page<StudentInfoResponse> findByStudentId_SsnEquals(Pageable pageable, String ssn);

    @Query("select s from StudentInfo s ")
    Page<StudentInfoResponse> getAll(Pageable pageable);

    @Query("select s from StudentInfo s where s.teacherId.ssn = ?1")
    Page<StudentInfoResponse> findByTeacherId_SsnEquals(String ssn, Pageable pageable);


 }
