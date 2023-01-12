package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.entity.concretes.Student;
import com.project.schoolmanagment.entity.concretes.Teacher;
import com.project.schoolmanagment.payload.request.ChooseLessonRequest;
import com.project.schoolmanagment.payload.request.StudentRequest;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.payload.response.StudentResponse;
import com.project.schoolmanagment.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("student")
@RequiredArgsConstructor
@CrossOrigin
public class StudentController {

    private final StudentService studentService;

    @PreAuthorize("hasAnyAuthority('ADMIN','ASSISTANTMANAGER')")
    @PostMapping("/save")
    public ResponseMessage<StudentResponse> save(@RequestBody @Valid StudentRequest studentRequest) {
        return studentService.save(studentRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','ASSISTANTMANAGER')")
    @GetMapping("/getAll")
    public List<StudentResponse> getAllTeacher() {
        return studentService.getAllStudent();
    }

    @PutMapping("/update/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','ASSISTANTMANAGER')")
    public ResponseMessage<StudentResponse> updateStudent(@PathVariable Long userId, @RequestBody @Valid StudentRequest studentRequest) {
        return studentService.updateStudent(userId, studentRequest);
    }

    @DeleteMapping("/delete/{studentId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','ASSISTANTMANAGER')")
    public ResponseMessage deleteStudent(@PathVariable Long studentId) {
        return studentService.deleteStudent(studentId);
    }

    @GetMapping("/getStudentByName")
    @PreAuthorize("hasAnyAuthority('ADMIN','ASSISTANTMANAGER')")
    public List<StudentResponse> getStudentByName(@RequestParam(name = "name") String studentName) {
        return studentService.getStudentByName(studentName);
    }

    @GetMapping("/getStudentBySnn")
    @PreAuthorize("hasAnyAuthority('ADMIN','ASSISTANTMANAGER')")
    public  StudentResponse getStudentBySnn(@RequestParam(name = "ssn") String ssn) {
        return studentService.getStudentBySnn(ssn);
    }
    @PostMapping("/chooseLesson")
    @PreAuthorize("hasAnyAuthority('STUDENT','ADMIN')")
    public ResponseMessage<StudentResponse> chooseLesson(@RequestBody ChooseLessonRequest chooseLessonRequest){
        return studentService.chooseLesson(chooseLessonRequest);
    }

    @PreAuthorize("hasAnyAuthority('TEACHER','ADMIN')")
    @GetMapping("/getAllByAdvisorId/{advisorId}")
    public List<StudentResponse> getAllStudentByAdvisorId(@PathVariable Long advisorId){
        return studentService.getAllStudentByAdvisorId(advisorId);
    }
    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('ADMIN','ASSISTANTMANAGER')")
    public Page<StudentResponse> search(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort") String sort,
            @RequestParam(value = "type") String type
    ) {
        return studentService.search(page, size, sort, type);
    }
}
