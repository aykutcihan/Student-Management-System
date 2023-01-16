package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.entity.concretes.Meet;
import com.project.schoolmanagment.payload.request.MeetRequestWithoutId;
import com.project.schoolmanagment.payload.request.UpdateRequest.UpdateMeetRequest;
import com.project.schoolmanagment.payload.response.MeetResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.service.MeetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("meet")
@RequiredArgsConstructor
@CrossOrigin
public class MeetController {

    private final MeetService meetService;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('TEACHER')")
    public ResponseMessage<MeetResponse> save(
            HttpServletRequest httpServletRequest,
            @RequestBody @Valid MeetRequestWithoutId meetRequest
    ) {
        String ssn = (String) httpServletRequest.getAttribute("ssn");
        return meetService.save(ssn,meetRequest);
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

    @GetMapping("/getAllMeetByAdvisorAsPage")
    @PreAuthorize("hasAnyAuthority('TEACHER','ADMIN')")
    public ResponseEntity<Page<MeetResponse>> getAllMeetByAdvisorAsPage(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size
    ) {
        String ssn = (String) httpServletRequest.getAttribute("ssn");
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<MeetResponse> meet = meetService.getAllMeetByAdvisorTeacherAsPage(pageable, ssn);
        return new ResponseEntity<>(meet, HttpStatus.OK);
    }

    @GetMapping("/getAllMeetByAdvisorTeacherAsList")
    @PreAuthorize("hasAnyAuthority('TEACHER','ADMIN')")
    public ResponseEntity<List<MeetResponse>> getAllMeetByAdvisorTeacherAsList(
            HttpServletRequest httpServletRequest
    ) {
        String ssn = (String) httpServletRequest.getAttribute("ssn");
        List<MeetResponse> meet = meetService.getAllMeetByAdvisorTeacherAsList(ssn);
        return new ResponseEntity<>(meet, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('TEACHER','ADMIN')")
    @DeleteMapping("/delete/{meetId}")
    public ResponseMessage delete(@PathVariable Long meetId) {
        return meetService.delete(meetId);
    }


    @PreAuthorize("hasAnyAuthority('TEACHER','ADMIN')")
    @PutMapping("/update/{meetId}")
    public ResponseMessage<MeetResponse> update(@RequestBody @Valid UpdateMeetRequest meetRequest, @PathVariable Long meetId) {
        return meetService.update(meetRequest, meetId);
    }

    @PreAuthorize("hasAnyAuthority( 'STUDENT')")
    @GetMapping("/getAllMeetByStudent" )
    public List<MeetResponse> getAllMeetByStudent(
            HttpServletRequest httpServletRequest
    ) {
        String ssn = (String) httpServletRequest.getAttribute("ssn");
        return meetService.getAllMeetByStudentBySsn(ssn);
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
