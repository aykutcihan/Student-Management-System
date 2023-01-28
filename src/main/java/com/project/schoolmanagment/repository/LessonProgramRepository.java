package com.project.schoolmanagment.repository;

import com.project.schoolmanagment.entity.concretes.LessonProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface LessonProgramRepository extends JpaRepository<LessonProgram, Long> {

    @Query(value = "SELECT l FROM LessonProgram l WHERE l.id IN :lessonProgramIdList")
    Set<LessonProgram> getLessonProgramByLessonProgramIdList( Set<Long> lessonProgramIdList);

    @Query("select l from LessonProgram l inner join l.students students where students.username = ?1")
    List<LessonProgram> getLessonProgramByStudentUsername(String username);


    @Query("select l from LessonProgram l inner join l.teachers teachers where teachers.username = ?1")
    List<LessonProgram> getLessonProgramByTeacherUsername(String username);
}
