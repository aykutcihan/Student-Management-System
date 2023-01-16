package com.project.schoolmanagment.repository;

import com.project.schoolmanagment.entity.concretes.Meet;
import com.project.schoolmanagment.payload.response.MeetResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MeetRepository extends JpaRepository<Meet, Long> {

    List<Meet> getAllMeetByAdvisorTeacher_Id(Long advisorId);

    @Query("select m from Meet m where m.advisorTeacher.teacher.ssn = ?1")
    Page<MeetResponse> findByAdvisorTeacher_Teacher_SsnEquals(Pageable pageable, String ssn);
    @Query("select m from Meet m where m.advisorTeacher.teacher.ssn = ?1")
    List<MeetResponse> findByAdvisorTeacher_Teacher_SsnEqualsAsList(String ssn);
    List<Meet> getAllMeetByStudent_Id(Long advisorId);

}
