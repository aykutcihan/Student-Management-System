

package com.project.schoolmanagment.repository;

import com.project.schoolmanagment.entity.concretes.Role;
import com.project.schoolmanagment.entity.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("select (count(r) > 0) from Role r where r.roleType = ?1")
    boolean existsByERoleEquals(RoleType roleType);
    @Query("select r from Role r where r.roleType = ?1")
    Optional<Role> findByERoleEquals(RoleType roleType);




}
