package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.payload.request.StudentInfoRequest;
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

    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    @PostMapping("/save")
    public ResponseMessage<StudentInfoResponse> save(@RequestBody @Valid StudentInfoRequest studentInfoRequest) {
        return studentInfoService.save(studentInfoRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    @DeleteMapping("/delete/{studentInfoId}")
    public ResponseMessage delete(@PathVariable Long studentInfoId) {
        return studentInfoService.deleteStudentInfo(studentInfoId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    @PutMapping("/update/{studentInfoId}")
    public ResponseMessage<StudentInfoResponse> update(@RequestBody @Valid UpdateStudentInfoRequest studentInfoRequest, @PathVariable Long studentInfoId) {
        return studentInfoService.update(studentInfoRequest, studentInfoId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    @GetMapping("/getAll")
    public List<StudentInfoResponse> getAll() {
        return studentInfoService.getAll();
    }


    @GetMapping("/getAllByStudent")
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public ResponseEntity<Page<StudentInfoResponse>> getAllByStudent(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size
    ) {
        String ssn = (String) httpServletRequest.getAttribute("ssn");
        Pageable pageable = PageRequest.of(page, size, Sort.by("lessonName").descending());
        Page<StudentInfoResponse> studentInfoResponse = studentInfoService.getAllStudentInfoByStudent(pageable, ssn);
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
