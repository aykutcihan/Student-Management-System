package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.entity.concretes.LessonProgram;
import com.project.schoolmanagment.entity.concretes.Meet;
import com.project.schoolmanagment.payload.request.MeetRequest;
import com.project.schoolmanagment.payload.request.UpdateRequest.UpdateMeetRequest;
import com.project.schoolmanagment.payload.response.MeetResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.service.MeetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("meet")
@RequiredArgsConstructor
@CrossOrigin
public class MeetController {

    private final MeetService meetService;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('TEACHER','ADMIN')")
    public ResponseMessage<MeetResponse> save(@RequestBody @Valid MeetRequest meetRequest) {
        return meetService.save(meetRequest);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('TEACHER','ADMIN')")
    public List<MeetResponse> getAll() {
        return meetService.getAll();
    }

    @GetMapping("/getMeetById/{meetId}")
    @PreAuthorize("hasAnyAuthority('TEACHER','ADMIN')")
    public ResponseMessage<MeetResponse> getMeetById(@PathVariable Long meetId) {
        return meetService.getMeetById(meetId);
    }

    @GetMapping("/getAllMeetByAdvisor/{advisorId}")
    @PreAuthorize("hasAnyAuthority('TEACHER','ADMIN')")
    public List<MeetResponse> getAllMeetByAdvisorTeacher(@PathVariable Long advisorId) {
        return meetService.getAllMeetByAdvisorTeacher(advisorId);
    }

    @PreAuthorize("hasAnyAuthority('TEACHER','ADMIN')")
    @DeleteMapping("/delete/{meetId}")
    public ResponseMessage delete(@PathVariable Long meetId){
        return meetService.delete(meetId);
    }


    @PreAuthorize("hasAnyAuthority('TEACHER','ADMIN')")
    @PutMapping("/update/{meetId}")
    public ResponseMessage<MeetResponse> update(@RequestBody @Valid UpdateMeetRequest meetRequest, @PathVariable Long meetId){
        return meetService.update(meetRequest,meetId);
    }

    @PreAuthorize("hasAnyAuthority('TEACHER','ADMIN','STUDENT')")
    @GetMapping("/getAllMeetByStudent/{studentId}")
    public List<MeetResponse> getAllMeetByStudent(@PathVariable Long studentId) {
        return meetService.getAllMeetByStudent(studentId);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','ASSISTANTMANAGER','TEACHER')")
    @GetMapping("/search")
    public Page<MeetResponse> search(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort") String sort,
            @RequestParam(value = "type") String type
    ) {
        return meetService.search(page, size, sort, type);
    }
}
