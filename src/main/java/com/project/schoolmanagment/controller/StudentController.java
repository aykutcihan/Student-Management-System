package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.entity.concretes.Student;
import com.project.schoolmanagment.entity.concretes.Teacher;
import com.project.schoolmanagment.payload.request.StudentRequest;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/save")
    public ResponseEntity<ResponseMessage<Student>> save(@RequestBody @Valid StudentRequest studentRequest){
       return ResponseEntity.ok(studentService.save(studentRequest));
    }
    @GetMapping("/getAllStudent")
    public ResponseEntity<List<Student>> getAllTeacher(){
        return ResponseEntity.ok(studentService.getAllStudent());
    }
    @PutMapping("/updateStudent/{userId}")
    public ResponseEntity<ResponseMessage<Student>> updateStudent(@PathVariable Long userId,@RequestBody StudentRequest studentRequest){
        return ResponseEntity.ok(studentService.updateStudent(userId,studentRequest));
    }

    @DeleteMapping("/deleteStudent/{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long studentId){
        return ResponseEntity.ok(studentService.deleteStudent(studentId));
    }
    @GetMapping("/searchStudentByName")
    public ResponseEntity<List<Student>> getStudentByName(@RequestParam(name = "name") String studentName){
        return ResponseEntity.ok(studentService.getStudentByName(studentName));
    }
}
