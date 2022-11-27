package com.project.schoolmanagment.repository;

import com.project.schoolmanagment.entity.concretes.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {
    boolean existsBySsn(String ssn);
}
