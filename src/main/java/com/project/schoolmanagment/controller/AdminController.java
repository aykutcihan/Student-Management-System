package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.entity.concretes.Admin;
import com.project.schoolmanagment.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Admin admin){
        return ResponseEntity.ok(adminService.save(admin));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> save(@PathVariable Long id){
        return ResponseEntity.ok(adminService.deleteAdmin(id));
    }

}
