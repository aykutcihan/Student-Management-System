package com.project.schoolmanagment.repository;

import com.project.schoolmanagment.entity.concretes.Meet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetRepository extends JpaRepository<Meet, Long> {


    Page<Meet> findByAdvisorTeacher_IdEquals(Long id, Pageable pageable);

    List<Meet> getByAdvisorTeacher_IdEquals(Long id);



    List<Meet> findByStudentList_IdEquals(Long id);








}
