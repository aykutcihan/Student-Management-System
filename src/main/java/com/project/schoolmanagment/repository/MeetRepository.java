package com.project.schoolmanagment.repository;

import com.project.schoolmanagment.entity.concretes.Meet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetRepository extends JpaRepository<Meet, Long> {

    List<Meet> getAllMeetByAdvisorTeacher_Id(Long advisorId);

    List<Meet> getAllMeetByStudent_Id(Long advisorId);
}
