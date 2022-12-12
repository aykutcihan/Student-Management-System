package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.entity.concretes.Student;
import com.project.schoolmanagment.entity.concretes.Teacher;
import com.project.schoolmanagment.payload.request.ChooseLessonRequest;
import com.project.schoolmanagment.payload.request.StudentRequest;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.payload.response.StudentResponse;
import com.project.schoolmanagment.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/save")
    public ResponseMessage<StudentResponse> save(@RequestBody @Valid StudentRequest studentRequest) {
        return studentService.save(studentRequest);
    }

    @GetMapping("/getAll")
    public List<StudentResponse> getAllTeacher() {
        return studentService.getAllStudent();
    }

    @PutMapping("/update/{userId}")
    public ResponseMessage<StudentResponse> updateStudent(@PathVariable Long userId, @RequestBody StudentRequest studentRequest) {
        return studentService.updateStudent(userId, studentRequest);
    }

    @DeleteMapping("/delete/{studentId}")
    public ResponseMessage deleteStudent(@PathVariable Long studentId) {
        return studentService.deleteStudent(studentId);
    }

    @GetMapping("/getStudentByName")
    public List<StudentResponse> getStudentByName(@RequestParam(name = "name") String studentName) {
        return studentService.getStudentByName(studentName);
    }
    @PutMapping("/chooseLesson")
    public ResponseMessage<StudentResponse> chooseLesson(@RequestBody ChooseLessonRequest chooseLessonRequest){
        return studentService.chooseLesson(chooseLessonRequest);
    }

    @GetMapping("/getAllByAdvisorId/{advisorId}")
    public List<StudentResponse> getAllTeacherByAdvisorId(@PathVariable Long advisorId){
        return studentService.getAllStudentByAdvisorId(advisorId);
    }
}
