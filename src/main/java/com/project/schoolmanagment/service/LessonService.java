package com.project.schoolmanagment.service;

import com.project.schoolmanagment.entity.concretes.Lesson;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.repository.LessonRepository;
import com.project.schoolmanagment.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;

    public ResponseMessage<Lesson> save(Lesson lesson) {
        if (existsLessonByLessonName(lesson.getLessonName())) {
            return ResponseMessage.<Lesson>builder().message("this lesson already register").build();
        }
        return ResponseMessage.<Lesson>builder().object(lessonRepository.save(lesson)).build();
    }

    public String deleteLesson(Long id) {
        Optional<Lesson> teacher = lessonRepository.findById(id);
        if (teacher.isPresent()) {
            lessonRepository.deleteById(id);
            return "Lesson deleted Successful";
        }
        return Messages.NOT_FOUND_LESSON_MESSAGE;
    }

    public ResponseMessage<Lesson> getLessonByLessonName(String lessonName) {
        Optional<Lesson> lesson = lessonRepository.getLessonByLessonName(lessonName);
        if (lesson.isPresent()) {
            return ResponseMessage.<Lesson>builder().message("Success")
                    .object(lesson.get()).build();
        }
        return ResponseMessage.<Lesson>builder().message("Not found lesson").build();
    }

    public boolean existsLessonByLessonName(String lessonName) {
        return lessonRepository.existsLessonByLessonNameEqualsIgnoreCase(lessonName);
    }

    public List<Lesson> getAllLesson() {
        return lessonRepository.findAll();
    }

    public Set<Lesson> getLessonByLessonNameList(Set<Long> lessons) {
        return lessonRepository.getLessonByLessonIdList(lessons);
    }


}
