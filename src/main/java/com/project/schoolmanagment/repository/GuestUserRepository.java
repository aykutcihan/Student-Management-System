package com.project.schoolmanagment.repository;

import com.project.schoolmanagment.entity.concretes.GuestUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GuestUserRepository extends JpaRepository<GuestUser,Long> {
    @Query("select (count(g) > 0) from GuestUser g where g.id = ?1")
    boolean existsByIdEquals(Long id);
    @Query("select (count(g) > 0) from GuestUser g where g.ssn = ?1")
    boolean existsBySsn(String ssn);

    @Query("select (count(g) > 0) from GuestUser g where g.phoneNumber = ?1")
    boolean existsByPhoneNumber(String phoneNumber);


    boolean existsByUsername(String username);

 }
