package com.project.schoolmanagment.repository;

import com.project.schoolmanagment.entity.concretes.AdvisorTeacher;
 import org.springframework.data.jpa.repository.JpaRepository;

 import java.util.Optional;

public interface AdvisoryTeacherRepository extends JpaRepository<AdvisorTeacher,Long> {
    Optional<AdvisorTeacher> findByTeacher_UsernameEquals(String username);
    boolean existsById(Long advisorTeacherId);
    Optional<AdvisorTeacher> getAdvisorTeacherByTeacher_Id(Long advisorTeacherId);
    AdvisorTeacher getAdvisorTeacherByTeacher_Ssn(String ssn);



    Optional<AdvisorTeacher>  findByTeacher_SsnEquals(String ssn);
}
