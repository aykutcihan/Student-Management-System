package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.entity.concretes.AdvisorTeacher;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.service.AdvisorTeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/advisorTeacher")
@RequiredArgsConstructor
public class AdvisorTeacherController {

    private final AdvisorTeacherService advisorTeacherService;

    @DeleteMapping("/deleteAdvisorTeacher/{id}")
    public ResponseEntity<String> deleteAdvisorTeacher(@PathVariable Long id){
        return ResponseEntity.ok(advisorTeacherService.deleteAdvisorTeacher(id));
    }
    @GetMapping("/getAllAdvisorTeacher")
    public ResponseEntity<List<AdvisorTeacher>> getAllAdvisorTeacher(){
        return ResponseEntity.ok(advisorTeacherService.getAllAdvisorTeacher());
    }
}
