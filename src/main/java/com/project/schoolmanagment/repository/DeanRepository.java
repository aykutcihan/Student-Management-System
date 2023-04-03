package com.project.schoolmanagment.repository;

import com.project.schoolmanagment.entity.concretes.Dean;
import com.project.schoolmanagment.entity.concretes.ViceDean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeanRepository extends JpaRepository<Dean,Long> {
    Dean findByUsernameEquals(String username);
    Dean getDeanBySsn(String ssn);
    boolean existsBySsn(String ssn);
    boolean existsByPhoneNumber(String ssn);

    boolean existsByUsername(String username);

 }
