package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.entity.concretes.Meet;
import com.project.schoolmanagment.payload.request.MeetRequest;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.service.MeetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("meet")
@RequiredArgsConstructor
public class MeetController {

    private final MeetService meetService;

    @PostMapping("/save")
    public ResponseMessage<Meet> save(@RequestBody @Valid MeetRequest meetRequest) {
        return meetService.save(meetRequest);
    }

    @GetMapping("/getAll")
    public List<Meet> getAll() {
        return meetService.getAll();
    }

    @GetMapping("/getMeetById/{meetId}")
    public ResponseMessage<Meet> getMeetById(@PathVariable Long meetId) {
        return meetService.getMeetById(meetId);
    }

    @GetMapping("/getAllMeetByAdvisor/{advisorId}")
    public List<Meet> getAllMeetByAdvisorTeacher(@PathVariable Long advisorId) {
        return meetService.getAllMeetByAdvisorTeacher(advisorId);
    }

    @DeleteMapping("/delete/{meetId}")
    public ResponseMessage delete(Long meetId){
        return meetService.delete(meetId);
    }

    /*
    @PutMapping("/update/{meetId}")
    public ResponseMessage<Meet> update(@RequestBody MeetRequest meetRequest,@PathVariable Long meetId){
        return meetService.update(meetRequest,meetId);
    }
     */

    @GetMapping("/getAllMeetByStudent/{studentId}")
    public List<Meet> getAllMeetByStudent(@PathVariable Long studentId) {
        return meetService.getAllMeetByStudent(studentId);
    }
}
