package com.project.schoolmanagment.repository;

import com.project.schoolmanagment.entity.concretes.AdvisorTeacher;
import com.project.schoolmanagment.entity.concretes.AssistantManager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssistantManagerRepository extends JpaRepository<AssistantManager,Long> {

    boolean existsBySsn(String ssn);
    boolean existsByPhoneNumber(String ssn);
    AssistantManager getAssistantManagerBySsn(String ssn);
}
