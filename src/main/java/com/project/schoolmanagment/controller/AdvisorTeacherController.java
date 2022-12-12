package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.entity.concretes.AdvisorTeacher;
import com.project.schoolmanagment.entity.concretes.Student;
import com.project.schoolmanagment.payload.response.AdvisorTeacherResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.service.AdvisorTeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("advisorTeacher")
@RequiredArgsConstructor
public class AdvisorTeacherController {

    private final AdvisorTeacherService advisorTeacherService;

    @DeleteMapping("/delete/{id}")
    public ResponseMessage deleteAdvisorTeacher(@PathVariable Long id) {
        return advisorTeacherService.deleteAdvisorTeacher(id);
    }

    @GetMapping("/getAll")
    public List<AdvisorTeacherResponse> getAllAdvisorTeacher() {
        return advisorTeacherService.getAllAdvisorTeacher();
    }
}
