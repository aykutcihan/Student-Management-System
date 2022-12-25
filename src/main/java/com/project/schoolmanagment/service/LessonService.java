package com.project.schoolmanagment.service;

import com.project.schoolmanagment.Exception.BadRequestException;
import com.project.schoolmanagment.Exception.ConflictException;
import com.project.schoolmanagment.Exception.ResourceNotFoundException;
import com.project.schoolmanagment.entity.concretes.Dean;
import com.project.schoolmanagment.entity.concretes.Lesson;
import com.project.schoolmanagment.payload.request.LessonRequest;
import com.project.schoolmanagment.payload.response.LessonResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.repository.LessonRepository;
import com.project.schoolmanagment.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;

    public ResponseMessage<LessonResponse> save(LessonRequest lessonRequest) {
        if (existsLessonByLessonName(lessonRequest.getLessonName())) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_LESSON, lessonRequest.getLessonName()));
        }
        Lesson lesson = createLessonObject(lessonRequest);
        return ResponseMessage.<LessonResponse>builder()
                .object(createLessonResponse(lessonRepository.save(lesson)))
                .message("Lesson Created")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public ResponseMessage deleteLesson(Long id) {
        Optional<Lesson> teacher = lessonRepository.findById(id);
        if (!teacher.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_LESSON_MESSAGE, id));
        }
        lessonRepository.deleteById(id);
        return ResponseMessage.builder()
                .message("Lesson Deleted")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public ResponseMessage<LessonResponse> getLessonByLessonName(String lessonName) {
        Optional<Lesson> lesson = lessonRepository.getLessonByLessonName(lessonName);
        if (!lesson.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_LESSON_NAME_MESSAGE, lessonName));
        }
        return ResponseMessage.<LessonResponse>builder().message("Lesson successfully found")
                .object(createLessonResponse(lesson.get())).build();
    }

    public Optional<Lesson> getLessonById(Long lessonId){
        return lessonRepository.findById(lessonId);
    }
    public boolean existsLessonByLessonName(String lessonName) {
        return lessonRepository.existsLessonByLessonNameEqualsIgnoreCase(lessonName);
    }

    public List<LessonResponse> getAllLesson() {
        return lessonRepository.findAll().stream().map(this::createLessonResponse).collect(Collectors.toList());
    }

    public Set<Lesson> getLessonByLessonNameList(Set<Long> lessons) {
        return lessonRepository.getLessonByLessonIdList(lessons);
    }

    private LessonResponse createLessonResponse(Lesson lesson){
       return LessonResponse.builder().lessonId(lesson.getLessonId()).lessonName(lesson.getLessonName()).build();
    }

    private Lesson createLessonObject(LessonRequest lessonRequest){
       return Lesson.builder().lessonName(lessonRequest.getLessonName()).build();
    }

    public Page<Lesson> search(int page, int size, String sort, String type) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }

        return lessonRepository.findAll(pageable);
    }
}
