package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.payload.request.EducationTermRequest;
import com.project.schoolmanagment.payload.response.EducationTermResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.service.EducationTermService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("educationTerms")
@RequiredArgsConstructor
@CrossOrigin
public class EducationTermController {

    private final EducationTermService educationTermService;

    @PostMapping("")
    public ResponseMessage<EducationTermResponse> save(@RequestBody @Valid EducationTermRequest educationTermRequest) {
        return educationTermService.save(educationTermRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANTMANAGER')")
    @GetMapping("/{id}")
    public EducationTermResponse get(
            @PathVariable Long id
    ) {
        return educationTermService.get(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANTMANAGER')")
    @GetMapping("/getAll")
    public List<EducationTermResponse> getAll() {
        return educationTermService.getAll();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANTMANAGER')")
    @GetMapping("/search")
    public Page<EducationTermResponse> getAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "startDate") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ) {
        return educationTermService.search(page, size, sort, type);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANTMANAGER')")
    @DeleteMapping("/{id}")
    public ResponseMessage<EducationTermResponse> delete(
            @PathVariable Long id
    ) {
        return educationTermService.delete(id);
    }


}
