package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.entity.concretes.LessonProgram;
import com.project.schoolmanagment.payload.request.LessonProgramRequest;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.service.LessonProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("lessonProgram")
@RequiredArgsConstructor
public class LessonProgramController {

    private final LessonProgramService lessonProgramService;

    @PostMapping("/save")
    public ResponseMessage<LessonProgram> save(@RequestBody LessonProgramRequest lessonProgramRequest) {
        return lessonProgramService.save(lessonProgramRequest);
    }

    /*
    @GetMapping("/getAllLessonProgramByTeacherId")
    public ResponseMessage<List<LessonProgram>> getAllLessonProgramByTeacherId(Long teacherId){
        return lessonProgramService.getLessonProgramById()
    }
     */
}
