package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.entity.concretes.Teacher;
import com.project.schoolmanagment.payload.request.TeacherRequest;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.payload.response.TeacherResponse;
import com.project.schoolmanagment.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping("/save")
    public ResponseMessage<TeacherResponse> save(@RequestBody @Valid TeacherRequest teacher) {
        return teacherService.save(teacher);
    }

    @GetMapping("/getAll")
    public List<Teacher> getAllTeacher() {
        return teacherService.getAllTeacher();
    }

    @PutMapping("/update/{userId}")
    public ResponseMessage<TeacherResponse> updateTeacher(@RequestBody @Valid TeacherRequest teacher, @PathVariable Long userId) {
        return teacherService.updateTeacher(teacher, userId);
    }

    @GetMapping("/getTeacherByName")
    public List<Teacher> getTeacherByName(@RequestParam(name = "name") String teacherName) {
        return teacherService.getTeacherByName(teacherName);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseMessage<?> deleteTeacher(@PathVariable Long id) {
        return teacherService.deleteTeacher(id);
    }

    @GetMapping("/getSavedTeacherById/{id}")
    public ResponseMessage<TeacherResponse> getSavedTeacherById(@PathVariable Long id) {
        return teacherService.getSavedTeacherById(id);
    }
}
