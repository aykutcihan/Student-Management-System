package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.entity.concretes.Lesson;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/lesson")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @PostMapping("/save")
    public ResponseEntity<ResponseMessage<Lesson>> save(@RequestBody @Valid Lesson lesson) {
        return ResponseEntity.ok(lessonService.save(lesson));
    }

    @DeleteMapping("/deleteLesson/{id}")
    public ResponseEntity<String> deleteLesson(@PathVariable Long id) {
        return ResponseEntity.ok(lessonService.deleteLesson(id));
    }

    @GetMapping("/getLessonByName")
    public ResponseEntity<ResponseMessage<Lesson>> getLessonByLessonName(@RequestParam String lessonName) {
        return ResponseEntity.ok(lessonService.getLessonByLessonName(lessonName));
    }

    @GetMapping("/getAllLesson")
    public ResponseEntity<List<Lesson>> getAllLesson() {
        return ResponseEntity.ok(lessonService.getAllLesson());
    }
    @GetMapping("/getAllLessonByLessonId")
    public ResponseEntity<Set<Lesson>> getAllLessonByLessonId(@RequestParam(name = "lessonId")
                                                                  Set<Long> idList){
        return ResponseEntity.ok(lessonService.getLessonByLessonNameList(idList));
    }
}
