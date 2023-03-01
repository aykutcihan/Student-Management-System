package com.project.schoolmanagment.controller;

 import com.project.schoolmanagment.payload.request.StudentInfoRequestWithoutTeacherId;
import com.project.schoolmanagment.payload.request.UpdateRequest.UpdateStudentInfoRequest;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.payload.response.StudentInfoResponse;
import com.project.schoolmanagment.service.StudentInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("studentInfo")
@RequiredArgsConstructor
@CrossOrigin
public class StudentInfoController {

    private final StudentInfoService studentInfoService;

    @PreAuthorize("hasAnyAuthority('TEACHER')")
    @PostMapping("/save")
    public ResponseMessage<StudentInfoResponse> save(
            HttpServletRequest httpServletRequest,
            @RequestBody @Valid StudentInfoRequestWithoutTeacherId studentInfoRequestWithoutTeacherId
    ) {
        String username = (String) httpServletRequest.getAttribute("username");
        return studentInfoService.save(username,studentInfoRequestWithoutTeacherId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    @DeleteMapping("/delete/{studentInfoId}")
    public ResponseMessage delete(@PathVariable Long studentInfoId) {
        return studentInfoService.deleteStudentInfo(studentInfoId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    @PutMapping("/update/{studentInfoId}")
    public ResponseMessage<StudentInfoResponse> update(
            @RequestBody @Valid UpdateStudentInfoRequest studentInfoRequest,
            @PathVariable Long studentInfoId
    ) {
        return studentInfoService.update(studentInfoRequest, studentInfoId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/getAllForAdmin")
    public ResponseEntity<Page<StudentInfoResponse>> getAll(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<StudentInfoResponse> studentInfoResponse = studentInfoService.getAll(pageable);
        return new ResponseEntity<>(studentInfoResponse, HttpStatus.OK);
    }


    @PreAuthorize("hasAnyAuthority('TEACHER')")
    @GetMapping("/getAllForTeacher")
    public ResponseEntity<Page<StudentInfoResponse>> getAllForTeacher(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        String username = (String) httpServletRequest.getAttribute("username");
        Page<StudentInfoResponse> studentInfoResponse = studentInfoService.getAllForTeacher(pageable, username);
        return new ResponseEntity<>(studentInfoResponse, HttpStatus.OK);
    }


    @GetMapping("/getAllByStudent")
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public ResponseEntity<Page<StudentInfoResponse>> getAllByStudent(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size
    ) {
        String username = (String) httpServletRequest.getAttribute("username");
        Pageable pageable = PageRequest.of(page, size, Sort.by("lessonName").descending());
        Page<StudentInfoResponse> studentInfoResponse = studentInfoService.getAllStudentInfoByStudent(username, pageable);
        return new ResponseEntity<>(studentInfoResponse, HttpStatus.OK);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('ADMIN','ASSISTANTMANAGER')")
    public Page<StudentInfoResponse> search(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort") String sort,
            @RequestParam(value = "type") String type
    ) {
        return studentInfoService.search(page, size, sort, type);
    }
}
