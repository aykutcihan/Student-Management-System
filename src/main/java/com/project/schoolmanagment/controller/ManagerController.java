package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.entity.concretes.AssistantManager;
import com.project.schoolmanagment.entity.concretes.Manager;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("manager")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    @PostMapping("/save")
    public ResponseMessage<Manager> save(@RequestBody Manager manager) {
        return managerService.save(manager);
    }

    @PutMapping("/update/{userId}")
    public ResponseMessage<Manager> update(@RequestBody Manager manager, @PathVariable Long userId) {
        return managerService.update(manager, userId);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseMessage<?> deleteAssistantManager(@PathVariable Long userId) {
        return managerService.deleteManager(userId);
    }

    @GetMapping("/getManagerById/{userId}")
    public ResponseMessage<Manager> getAssistantManagerById(@PathVariable Long userId) {
        return managerService.getManagerById(userId);
    }

    @GetMapping("/getAll")
    public List<Manager> getAllAssistantManager() {
        return managerService.getAllManager();
    }
}
