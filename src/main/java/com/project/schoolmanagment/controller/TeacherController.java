package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.entity.concretes.Teacher;
import com.project.schoolmanagment.payload.request.ChooseLessonRequest;
import com.project.schoolmanagment.payload.request.ChooseLessonTeacherRequest;
import com.project.schoolmanagment.payload.request.TeacherRequest;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.payload.response.StudentResponse;
import com.project.schoolmanagment.payload.response.TeacherResponse;
import com.project.schoolmanagment.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("teachers")
@RequiredArgsConstructor
@CrossOrigin
public class TeacherController {

    private final TeacherService teacherService;

        @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANTMANAGER')")
    @PostMapping("/save")
    public ResponseMessage<TeacherResponse> save(
            @RequestBody @Valid TeacherRequest teacher
    ) {
        return teacherService.save(teacher);
    }

        @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANTMANAGER')")
    @GetMapping("/getAll")
    public List<TeacherResponse> getAllTeacher() {
        return teacherService.getAllTeacher();
    }

        @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANTMANAGER')")
    @PutMapping("/update/{userId}")
    public ResponseMessage<TeacherResponse> updateTeacher(@RequestBody @Valid TeacherRequest teacher, @PathVariable Long userId) {
        return teacherService.updateTeacher(teacher, userId);
    }

        @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANTMANAGER')")
    @GetMapping("/getTeacherByName")
    public List<TeacherResponse> getTeacherByName(@RequestParam(name = "name") String teacherName) {
        return teacherService.getTeacherByName(teacherName);
    }

        @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANTMANAGER')")
    @DeleteMapping("/delete/{id}")
    public ResponseMessage<?> deleteTeacher(@PathVariable Long id) {
        return teacherService.deleteTeacher(id);
    }

        @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANTMANAGER')")
    @GetMapping("/getSavedTeacherById/{id}")
    public ResponseMessage<TeacherResponse> getSavedTeacherById(@PathVariable Long id) {
        return teacherService.getSavedTeacherById(id);
    }

        @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANTMANAGER')")
    @PostMapping("/chooseLesson")
    public ResponseMessage<TeacherResponse> chooseLesson(@RequestBody ChooseLessonTeacherRequest chooseLessonRequest) {
        return teacherService.chooseLesson(chooseLessonRequest);
    }

        @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANTMANAGER')")
    @GetMapping("/search")
    public Page<TeacherResponse> search(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort") String sort,
            @RequestParam(value = "type") String type
    ) {
        return teacherService.search(page, size, sort, type);
    }
}
