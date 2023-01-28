package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.entity.concretes.Student;
import com.project.schoolmanagment.payload.request.ChooseLessonRequestWithoutId;
import com.project.schoolmanagment.payload.request.StudentRequest;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.payload.response.StudentResponse;
import com.project.schoolmanagment.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("students")
@RequiredArgsConstructor
@CrossOrigin
public class StudentController {

    private final StudentService studentService;

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANTMANAGER')")
    @PostMapping("/save")
    public ResponseMessage<StudentResponse> save(
            @RequestBody @Valid StudentRequest studentRequest
    ) {
        return studentService.save(studentRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANTMANAGER')")
    @GetMapping("/getAll")
    public List<StudentResponse> getAllTeacher() {
        return studentService.getAllStudent();
    }

    @PutMapping("/update/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANTMANAGER')")
    public ResponseMessage<StudentResponse> updateStudent(@PathVariable Long userId, @RequestBody @Valid StudentRequest studentRequest) {
        return studentService.updateStudent(userId, studentRequest);
    }

    @DeleteMapping("/delete/{studentId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANTMANAGER')")
    public ResponseMessage deleteStudent(@PathVariable Long studentId) {
        return studentService.deleteStudent(studentId);
    }

    @GetMapping("/getStudentByName")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANTMANAGER')")
    public List<StudentResponse> getStudentByName(@RequestParam(name = "name") String studentName) {
        return studentService.getStudentByName(studentName);
    }

    @GetMapping("/getStudentById")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANTMANAGER')")
    public Optional<Student> getStudentById(@RequestParam(name = "id") Long id) {
        return studentService.getStudentById(id);
    }

    @PostMapping("/chooseLesson")
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public ResponseMessage<StudentResponse> chooseLesson(
            HttpServletRequest request,
            @RequestBody ChooseLessonRequestWithoutId chooseLessonRequest
    ) {
        String username = (String) request.getAttribute("username");
        return studentService.chooseLesson(username, chooseLessonRequest);
    }

    @PreAuthorize("hasAnyAuthority('TEACHER')")
    @GetMapping("/getAllByAdvisorId")
    public List<StudentResponse> getAllStudentByAdvisorId(
            HttpServletRequest request
            ) {
        String username = (String) request.getAttribute("username");
        return studentService.getAllStudentByTeacher_Username(username);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANTMANAGER')")
    public Page<StudentResponse> search(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort") String sort,
            @RequestParam(value = "type") String type
    ) {
        return studentService.search(page, size, sort, type);
    }
}
