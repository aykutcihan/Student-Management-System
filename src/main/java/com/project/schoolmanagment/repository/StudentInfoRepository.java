package com.project.schoolmanagment.repository;

import com.project.schoolmanagment.entity.concretes.Student;
import com.project.schoolmanagment.entity.concretes.StudentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentInfoRepository extends JpaRepository<StudentInfo,Long> {

    List<StudentInfo> getAllByStudentId_Id(Long studentId_id);
}
