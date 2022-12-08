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
@RequestMapping("api/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping("/save")
    public ResponseEntity<ResponseMessage<TeacherResponse>> save(@RequestBody @Valid TeacherRequest teacher) {
        return ResponseEntity.ok(teacherService.save(teacher));
    }

    @GetMapping("/getAllTeacher")
    public ResponseEntity<List<Teacher>> getAllTeacher() {
        return ResponseEntity.ok(teacherService.getAllTeacher());
    }

    @PutMapping("/updateTeacher/{userId}")
    public ResponseEntity<ResponseMessage<TeacherResponse>> updateTeacher(@RequestBody @Valid TeacherRequest teacher, @PathVariable Long userId) {
        return ResponseEntity.ok(teacherService.updateTeacher(teacher, userId));
    }

    @GetMapping("/searchTeacherByName")
    public ResponseEntity<List<Teacher>> getTeacherByName(@RequestParam(name = "name") String teacherName) {
        return ResponseEntity.ok(teacherService.getTeacherByName(teacherName));
    }

    @DeleteMapping("/deleteTeacher/{id}")
    public ResponseEntity<ResponseMessage<?>> deleteTeacher(@PathVariable Long id) {
        return ResponseEntity.ok(teacherService.deleteTeacher(id));
    }

    @GetMapping("/getSavedTeacherById/{id}")
    public ResponseEntity<ResponseMessage<TeacherResponse>> getSavedTeacherById(@PathVariable Long id) {
        return ResponseEntity.ok(teacherService.getSavedTeacherById(id));
    }

}
