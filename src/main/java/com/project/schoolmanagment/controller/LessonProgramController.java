package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.payload.request.LessonProgramRequest;
import com.project.schoolmanagment.payload.response.LessonProgramResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.service.LessonProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    @PreAuthorize("hasAnyAuthority('ASSISTANTMANAGER','ADMIN','TEACHER','STUDENT')")
    public List<LessonProgramResponse> getAll() {
        return lessonProgramService.getAllLessonProgram();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ASSISTANTMANAGER','ADMIN')")
    public ResponseMessage delete(@PathVariable Long id) {
        return lessonProgramService.deleteLessonProgram(id);
    }

    @GetMapping("getAllLessonProgramByTeacher")
    @PreAuthorize("hasAnyAuthority( 'TEACHER')")
    public Set<LessonProgramResponse> getAllLessonProgramByTeacherId(
            HttpServletRequest httpServletRequest
    ) {
        String ssn = (String) httpServletRequest.getAttribute("ssn");
        return lessonProgramService.getLessonProgramByTeacherSsn(ssn);
    }


    @GetMapping("getAllLessonProgramByStudent")
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public Set<LessonProgramResponse> getAllLessonProgramByStudent(
            HttpServletRequest httpServletRequest
    ) {
        String ssn = (String) httpServletRequest.getAttribute("ssn");
        return lessonProgramService.getLessonProgramByStudentSsn(ssn);
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
