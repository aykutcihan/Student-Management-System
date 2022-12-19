package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.entity.concretes.AssistantManager;
import com.project.schoolmanagment.payload.request.AssistantManagerRequest;
import com.project.schoolmanagment.payload.response.AssistantManagerResponse;
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
    public ResponseMessage<AssistantManagerResponse> save(@RequestBody AssistantManagerRequest assistantManagerRequest) {
        return assistantManagerService.save(assistantManagerRequest);
    }

    @PutMapping("/update/{userId}")
    public ResponseMessage<AssistantManagerResponse> update(@RequestBody AssistantManagerRequest assistantManagerRequest, @PathVariable Long userId) {
        return assistantManagerService.update(assistantManagerRequest, userId);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseMessage<?> deleteAssistantManager(@PathVariable Long userId) {
        return assistantManagerService.deleteAssistantManager(userId);
    }

    @GetMapping("/getAssistantManagerById/{userId}")
    public ResponseMessage<AssistantManagerResponse> getAssistantManagerById(@PathVariable Long userId) {
        return assistantManagerService.getAssistantManagerById(userId);
    }

    @GetMapping("/getAll")
    public List<AssistantManagerResponse> getAllAssistantManager() {
        return assistantManagerService.getAllAssistantManager();
    }
}
