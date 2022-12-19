package com.project.schoolmanagment.repository;

import com.project.schoolmanagment.entity.concretes.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager,Long> {
    Manager getManagerBySsn(String ssn);
}
