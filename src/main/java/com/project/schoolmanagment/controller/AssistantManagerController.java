package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.entity.concretes.AssistantManager;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.service.AssistantManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("assistantManager")
@RequiredArgsConstructor
public class AssistantManagerController {

    private final AssistantManagerService assistantManagerService;

    @PostMapping("/save")
    public ResponseMessage<AssistantManager> save(@RequestBody AssistantManager assistantManager) {
        return assistantManagerService.save(assistantManager);
    }

    @PutMapping("/update/{userId}")
    public ResponseMessage<AssistantManager> update(@RequestBody AssistantManager assistantManager, @PathVariable Long userId) {
        return assistantManagerService.update(assistantManager, userId);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseMessage<?> deleteAssistantManager(@PathVariable Long userId) {
        return assistantManagerService.deleteAssistantManager(userId);
    }

    @GetMapping("/getAssistantManagerById/{userId}")
    public ResponseMessage<AssistantManager> getAssistantManagerById(@PathVariable Long userId) {
        return assistantManagerService.getAssistantManagerById(userId);
    }

    @GetMapping("/getAll")
    public List<AssistantManager> getAllAssistantManager() {
        return assistantManagerService.getAllAssistantManager();
    }
}
