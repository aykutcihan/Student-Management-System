package com.project.schoolmanagment.repository;

import com.project.schoolmanagment.entity.concretes.Admin;
import com.project.schoolmanagment.entity.concretes.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {
    Admin findBySsnEquals(String ssn);


    boolean existsBySsn(String ssn);

    boolean existsByPhoneNumber(String phoneNumber);
}
