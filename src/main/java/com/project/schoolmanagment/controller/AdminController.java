package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.entity.concretes.Admin;
import com.project.schoolmanagment.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> save(@RequestBody @Valid Admin admin) {
        return ResponseEntity.ok(adminService.save(admin));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<List<Admin>> getAll() {
        List<Admin> author = adminService.getAllAdmin();
        return new ResponseEntity<>(author, HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.deleteAdmin(id));
    }

}
