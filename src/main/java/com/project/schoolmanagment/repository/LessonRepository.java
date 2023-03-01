package com.project.schoolmanagment.repository;

import com.project.schoolmanagment.entity.concretes.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface LessonRepository extends JpaRepository<Lesson,Long> {
    boolean existsByLessonIdEquals(Long lessonId);
    Lesson findByLessonIdEquals(Long lessonId);
    Optional<Lesson> getLessonByLessonName(String name);

    boolean existsLessonByLessonNameEqualsIgnoreCase(String name);
    @Query(value = "SELECT l FROM Lesson l WHERE l.lessonId IN :lessons")
    Set<Lesson> getLessonByLessonIdList(@Param("lessons") Set<Long> lessons);

}
