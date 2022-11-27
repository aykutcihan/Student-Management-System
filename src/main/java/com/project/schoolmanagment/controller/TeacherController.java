package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.entity.concretes.Teacher;
import com.project.schoolmanagment.payload.request.TeacherRequest;
import com.project.schoolmanagment.payload.response.ResponseMessage;
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
    public ResponseEntity<ResponseMessage<Teacher>> save(@RequestBody @Valid
                                                         TeacherRequest teacher) {
        return ResponseEntity.ok(teacherService.save(teacher));
    }

    @GetMapping("/getAllTeacher")
    public ResponseEntity<List<Teacher>> getAllTeacher() {
        return ResponseEntity.ok(teacherService.getAllTeacher());
    }

    @PutMapping("/updateTeacher/{userId}")
    public ResponseEntity<ResponseMessage<Teacher>> updateTeacher(@RequestBody @Valid Teacher teacher, @PathVariable Long userId) {
        return ResponseEntity.ok(teacherService.updateTeacher(teacher, userId));
    }

    @DeleteMapping("/deleteTeacher/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable Long id) {
        return ResponseEntity.ok(teacherService.deleteTeacher(id));
    }

}
