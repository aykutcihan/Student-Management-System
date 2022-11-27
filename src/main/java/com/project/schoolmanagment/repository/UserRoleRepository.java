package com.project.schoolmanagment.repository;

import com.project.schoolmanagment.entity.concretes.Role;
import com.project.schoolmanagment.entity.concretes.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole,Long> {

    Optional<UserRole> getUserRoleByRole( Role role);
}
