

package com.project.schoolmanagment.repository;

import com.project.schoolmanagment.entity.concretes.UserRole;
import com.project.schoolmanagment.entity.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    @Query("select (count(r) > 0) from UserRole r where r.roleType = ?1")
    boolean existsByERoleEquals(RoleType roleType);
    @Query("select r from UserRole r where r.roleType = ?1")
    Optional<UserRole> findByERoleEquals(RoleType roleType);
}
