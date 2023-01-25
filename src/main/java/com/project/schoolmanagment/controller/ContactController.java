package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.payload.request.ContactMessageRequest;
import com.project.schoolmanagment.payload.request.ContactMessageResponse;
import com.project.schoolmanagment.service.ContactMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("contactMessages")
@RequiredArgsConstructor
@CrossOrigin
public class ContactController {

    private final ContactMessageService contactMessageService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody @Valid ContactMessageRequest contactMessageRequest) {
        return ResponseEntity.ok(contactMessageService.save(contactMessageRequest));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANTMANAGER')")
    @GetMapping("/getAll")
    public Page<ContactMessageResponse> getAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "date") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ) {
        return contactMessageService.getAll(page, size, sort, type);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANTMANAGER')")
    @GetMapping("/searchByEmail")
    public Page<ContactMessageResponse> searchByEmail(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "date") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ) {
        return contactMessageService.searchByEmail(email, page, size, sort, type);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANTMANAGER')")
    @GetMapping("/searchBySubject")
    public Page<ContactMessageResponse> searchBySubject(
            @RequestParam(value = "subject") String subject,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "date") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ) {
        return contactMessageService.searchBySubject(subject, page, size, sort, type);
    }

}
