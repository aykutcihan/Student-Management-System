package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.entity.concretes.Lesson;
import com.project.schoolmanagment.entity.concretes.LessonProgram;
import com.project.schoolmanagment.payload.request.LessonProgramRequest;
import com.project.schoolmanagment.payload.response.LessonProgramResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.service.LessonProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("lessonProgram")
@RequiredArgsConstructor
@CrossOrigin
public class LessonProgramController {

    private final LessonProgramService lessonProgramService;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ASSISTANTMANAGER','ADMIN')")
    public ResponseMessage<LessonProgramResponse> save(@RequestBody @Valid LessonProgramRequest lessonProgramRequest) {
        return lessonProgramService.save(lessonProgramRequest);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('ASSISTANTMANAGER','ADMIN')")
    public List<LessonProgramResponse> getAll(){
        return lessonProgramService.getAllLessonProgram();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ASSISTANTMANAGER','ADMIN')")
    public ResponseMessage delete(@PathVariable Long id){
        return lessonProgramService.deleteLessonProgram(id);
    }

    @GetMapping("getAllLessonProgramByTeacherId/{teacherId}")
    @PreAuthorize("hasAnyAuthority('ASSISTANTMANAGER','ADMIN','TEACHER')")
    public Set<LessonProgramResponse> getAllLessonProgramByTeacherId(@PathVariable Long teacherId){
        return lessonProgramService.getLessonProgramByTeacherId(teacherId);
    }

    @PreAuthorize("hasAnyAuthority('ASSISTANTMANAGER','ADMIN','STUDENT')")
    @GetMapping("getAllLessonProgramByStudentId/{studentId}")
    public Set<LessonProgramResponse> getAllLessonProgramByStudentId(@PathVariable Long studentId){
        return lessonProgramService.getLessonProgramByStudentId(studentId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','ASSISTANTMANAGER')")
    @GetMapping("/search")
    public Page<LessonProgramResponse> search(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort") String sort,
            @RequestParam(value = "type") String type
    ) {
        return lessonProgramService.search(page, size, sort, type);
    }
}
