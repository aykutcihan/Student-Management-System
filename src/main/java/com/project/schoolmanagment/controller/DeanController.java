package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.payload.request.DeanRequest;
import com.project.schoolmanagment.payload.response.DeanResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.service.DeanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("dean")
@RequiredArgsConstructor

public class DeanController {

    private final DeanService deanService;

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseMessage<DeanResponse> save(@RequestBody @Valid DeanRequest deanRequest) {
        return deanService.save(deanRequest);
    }

    @PutMapping("/update/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseMessage<DeanResponse> update(@RequestBody @Valid DeanRequest deanRequest, @PathVariable Long userId) {
        return deanService.update(deanRequest, userId);
    }

    @DeleteMapping("/delete/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseMessage<?> delete(@PathVariable Long userId) {
        return deanService.deleteDean(userId);
    }

    @GetMapping("/getManagerById/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseMessage<DeanResponse> getDeanById(@PathVariable Long userId) {
        return deanService.getDeanById(userId);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<DeanResponse> getAll() {
        return deanService.getAllDean();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/search")
    public Page<DeanResponse> search(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort") String sort,
            @RequestParam(value = "type") String type
    ) {
        return deanService.search(page, size, sort, type);
    }
}
