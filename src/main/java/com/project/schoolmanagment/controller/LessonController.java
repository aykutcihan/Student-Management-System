package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.entity.concretes.Lesson;
import com.project.schoolmanagment.payload.request.LessonRequest;
import com.project.schoolmanagment.payload.response.LessonResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("lesson")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @PostMapping("/save")
    public ResponseMessage<LessonResponse> save(@RequestBody @Valid LessonRequest lesson) {
        return lessonService.save(lesson);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseMessage deleteLesson(@PathVariable Long id) {
        return lessonService.deleteLesson(id);
    }

    @GetMapping("/getLessonByName")
    public ResponseMessage<LessonResponse> getLessonByLessonName(@RequestParam String lessonName) {
        return lessonService.getLessonByLessonName(lessonName);
    }

    @GetMapping("/getAll")
    public List<LessonResponse> getAllLesson() {
        return lessonService.getAllLesson();
    }

    @GetMapping("/getAllLessonByLessonId")
    public Set<Lesson> getAllLessonByLessonId(@RequestParam(name = "lessonId")
                                              Set<Long> idList) {
        return lessonService.getLessonByLessonNameList(idList);
    }
}
