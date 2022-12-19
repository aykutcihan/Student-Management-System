package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.entity.concretes.LessonProgram;
import com.project.schoolmanagment.payload.request.LessonProgramRequest;
import com.project.schoolmanagment.payload.response.LessonProgramResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.service.LessonProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("lessonProgram")
@RequiredArgsConstructor
public class LessonProgramController {

    private final LessonProgramService lessonProgramService;

    @PostMapping("/save")
    public ResponseMessage<LessonProgram> save(@RequestBody @Valid LessonProgramRequest lessonProgramRequest) {
        return lessonProgramService.save(lessonProgramRequest);
    }

    @GetMapping("/getAll")
    public List<LessonProgram> getAll(){
        return lessonProgramService.getAllLessonProgram();
    }

    @GetMapping("getAllLessonProgramByTeacherId/{teacherId}")
    public Set<LessonProgramResponse> getAllLessonProgramByTeacherId(@PathVariable Long teacherId){
        return lessonProgramService.getLessonProgramByTeacherId(teacherId);
    }

    @GetMapping("getAllLessonProgramByStudentId/{studentId}")
    public Set<LessonProgramResponse> getAllLessonProgramByStudentId(@PathVariable Long studentId){
        return lessonProgramService.getLessonProgramByStudentId(studentId);
    }
}
