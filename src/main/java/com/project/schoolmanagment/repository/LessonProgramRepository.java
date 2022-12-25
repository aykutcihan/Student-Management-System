package com.project.schoolmanagment.repository;

import com.project.schoolmanagment.entity.concretes.LessonProgram;
import com.project.schoolmanagment.entity.concretes.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface LessonProgramRepository extends JpaRepository<LessonProgram,Long> {

    @Query(value = "SELECT l FROM LessonProgram l WHERE l.id IN :lessonProgramIdList")
    Set<LessonProgram> getLessonProgramByLessonProgramIdList(@Param("lessonProgramIdList")
                                                             Set<Long> lessonProgramIdList);

    List<LessonProgram> getLessonProgramByTeachersId(Long teacherId);
    List<LessonProgram> getLessonProgramByStudentsId(Long studentId);


}
