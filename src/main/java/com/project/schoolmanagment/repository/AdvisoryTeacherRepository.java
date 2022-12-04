package com.project.schoolmanagment.repository;

import com.project.schoolmanagment.entity.concretes.AdvisorTeacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdvisoryTeacherRepository extends JpaRepository<AdvisorTeacher,Long> {
    boolean existsById(Long id);
}
