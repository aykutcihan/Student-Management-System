package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.entity.concretes.StudentInfo;
import com.project.schoolmanagment.payload.request.StudentInfoRequest;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.payload.response.StudentInfoResponse;
import com.project.schoolmanagment.service.StudentInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("studentInfo")
@RequiredArgsConstructor
public class StudentInfoController {

    private final StudentInfoService studentInfoService;

    @PostMapping("/save")
    public ResponseMessage<StudentInfoResponse> save(@RequestBody StudentInfoRequest studentInfoRequest) {
        return studentInfoService.save(studentInfoRequest);
    }

    @DeleteMapping("/delete/{studentInfoId}")
    public ResponseMessage delete(@PathVariable Long studentInfoId) {
        return studentInfoService.deleteStudentInfo(studentInfoId);
    }

    @PutMapping("/update/{studentInfoId}")
    public ResponseMessage<StudentInfoResponse> update(@RequestBody StudentInfoRequest studentInfoRequest,@PathVariable Long studentInfoId) {
        return studentInfoService.update(studentInfoRequest, studentInfoId);
    }
    @GetMapping("/getAll")
    public List<StudentInfoResponse> getAll() {
        return studentInfoService.getAll();
    }
    @GetMapping("/getAllByStudent/{studentId}")
    public List<StudentInfoResponse> getAllByStudent(@PathVariable Long studentId) {
        return studentInfoService.getAllStudentInfoByStudent(studentId);
    }
}
