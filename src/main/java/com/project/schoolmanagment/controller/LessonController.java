package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.entity.concretes.Lesson;
import com.project.schoolmanagment.payload.request.LessonRequest;
import com.project.schoolmanagment.payload.response.LessonResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("lessons")
@RequiredArgsConstructor

public class LessonController {

    private final LessonService lessonService;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANTMANAGER')")
    public ResponseMessage<LessonResponse> save(
            @RequestBody @Valid LessonRequest lesson
    ){
        return lessonService.save(lesson);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANTMANAGER')")
    @DeleteMapping("/delete/{id}")
    public ResponseMessage deleteLesson(@PathVariable Long id) {
        return lessonService.deleteLesson(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANTMANAGER')")
    @GetMapping("/getLessonByName")
    public ResponseMessage<LessonResponse> getLessonByLessonName(@RequestParam String lessonName) {
        return lessonService.getLessonByLessonName(lessonName);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANTMANAGER')")
    @GetMapping("/getAll")
    public List<LessonResponse> getAllLesson() {
        return lessonService.getAllLesson();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANTMANAGER')")
    @GetMapping("/getAllLessonByLessonId")

    public Set<Lesson> getAllLessonByLessonId(
            @RequestParam(name = "lessonId") Set<Long> idList
    ) {
        return lessonService.getLessonByLessonNameList(idList);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANTMANAGER')")
    @GetMapping("/search")
    public Page<LessonResponse> search(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort") String sort,
            @RequestParam(value = "type") String type
    ) {
        return lessonService.search(page, size, sort, type);
    }
}
