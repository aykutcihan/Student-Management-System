package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.payload.response.AdvisorTeacherResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
 import com.project.schoolmanagment.service.AdvisorTeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
 import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("advisorTeacher")
@RequiredArgsConstructor

public class AdvisorTeacherController {

    private final AdvisorTeacherService advisorTeacherService;

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','ASSISTANTMANAGER')")
    public ResponseMessage deleteAdvisorTeacher(@PathVariable Long id) {
        return advisorTeacherService.deleteAdvisorTeacher(id);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('ADMIN','ASSISTANTMANAGER')")
    public List<AdvisorTeacherResponse> getAllAdvisorTeacher() {
        return advisorTeacherService.getAllAdvisorTeacher();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','ASSISTANTMANAGER')")
    @GetMapping("/search")
    public Page<AdvisorTeacherResponse> search(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort") String sort,
            @RequestParam(value = "type") String type
    ) {
        return advisorTeacherService.search(page, size, sort, type);
    }
}
