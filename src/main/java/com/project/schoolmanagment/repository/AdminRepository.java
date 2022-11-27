package com.project.schoolmanagment.repository;

import com.project.schoolmanagment.entity.concretes.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {
}
