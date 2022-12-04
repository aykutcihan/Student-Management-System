package com.project.schoolmanagment.repository;

import com.project.schoolmanagment.entity.concretes.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> {
    boolean existsBySsn(String ssn);
    List<Student> getStudentByNameContaining(String name);
}
