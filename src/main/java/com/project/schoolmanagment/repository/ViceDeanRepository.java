package com.project.schoolmanagment.repository;

import com.project.schoolmanagment.entity.concretes.ViceDean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViceDeanRepository extends JpaRepository<ViceDean,Long> {
    ViceDean findByUsernameEquals(String username);

    boolean existsBySsn(String ssn);
    boolean existsByPhoneNumber(String ssn);
    ViceDean getViceDeanBySsn(String ssn);

    boolean existsByUsername(String username);

 }
