package com.project.schoolmanagment.service;

import com.project.schoolmanagment.Exception.BadRequestException;
import com.project.schoolmanagment.Exception.ResourceNotFoundException;
import com.project.schoolmanagment.entity.concretes.AdvisorTeacher;
import com.project.schoolmanagment.entity.concretes.Meet;
import com.project.schoolmanagment.entity.concretes.Student;
import com.project.schoolmanagment.payload.Dto.MeetDto;
import com.project.schoolmanagment.payload.request.MeetRequest;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.repository.MeetRepository;
import com.project.schoolmanagment.utils.Messages;
import com.project.schoolmanagment.utils.TimeControl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MeetService {

    private final MeetRepository meetRepository;
    private final AdvisorTeacherService advisorTeacherService;
    private final StudentService studentService;
    private final MeetDto meetDto;

    public ResponseMessage<Meet> save(MeetRequest meetRequest) {
        Optional<Student> student = studentService.getStudentById(meetRequest.getStudentId());
        Optional<AdvisorTeacher> advisorTeacher = advisorTeacherService.getAdvisorTeacherById(meetRequest.getAdvisorTeacherId());
        if (!student.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE, meetRequest.getStudentId()));
        } else if (!advisorTeacher.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_ADVISOR_MESSAGE, meetRequest.getAdvisorTeacherId()));
        }
        else if(TimeControl.check(meetRequest.getStartTime(),meetRequest.getStopTime())){
            throw new BadRequestException(Messages.TIME_NOT_VALID_MESSAGE);
        }
        List<Meet> meets = meetRepository.getAllMeetByStudent_Id(meetRequest.getStudentId());

        if(meets.size()>0){
            for(Meet meet : meets){
                if(meet.getDate().equals(meetRequest.getDate()) && meet.getStartTime().equals(meetRequest.getStartTime())){
                    throw new ResourceNotFoundException(Messages.MEET_EXIST_MESSAGE);
                }
            }
        }

        Meet meet = meetRequestToDto(meetRequest);
        meet.setAdvisorTeacher(advisorTeacher.get());
        meet.setStudent(student.get());
        Meet savedMeet = meetRepository.save(meet);
        try {
            EmailService.sendMail(student.get().getEmail()," Date "+savedMeet.getDate()
                    +"\n Start Time "+savedMeet.getStartTime()
                    +"\n Stop Time "+savedMeet.getStopTime()
            +"\n Advisor Teacher Name "+savedMeet.getAdvisorTeacher().getTeacher().getName()
            +"\n"+savedMeet.getDescription(),"Proje kapanış toplantısı");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return ResponseMessage.<Meet>builder().message("Meet Saved Successfully")
                .object(savedMeet)
                .httpStatus(HttpStatus.CREATED).build();
    }

    public ResponseMessage<Meet> getMeetById(Long meetId) {
        Optional<Meet> meet = meetRepository.findById(meetId);
        if (!meet.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.MEET_NOT_FOUND_MESSAGE, meetId));
        }
        return ResponseMessage.<Meet>builder().message("Meet successfully found")
                .object(meet.get())
                .httpStatus(HttpStatus.CREATED).build();
    }

    public List<Meet> getAll() {
        return meetRepository.findAll();
    }

    public List<Meet> getAllMeetByAdvisorTeacher(Long advisorId) {
        Optional<AdvisorTeacher> advisorTeacher = advisorTeacherService.getAdvisorTeacherById(advisorId);
        if (!advisorTeacher.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_ADVISOR_MESSAGE, advisorId));
        }
        return meetRepository.getAllMeetByAdvisorTeacher_Id(advisorId);
    }

    private Meet meetRequestToDto(MeetRequest meetRequest) {
        return meetDto.meetDto(meetRequest);
    }

    public List<Meet> getAllMeetByStudent(Long studentId) {
        Optional<Student> student = studentService.getStudentById(studentId);
        if (!student.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE, studentId));
        }
        return meetRepository.getAllMeetByStudent_Id(studentId);
    }

    public ResponseMessage delete(Long meetId) {
        Optional<Meet> meet = meetRepository.findById(meetId);
        if (!meet.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.MEET_NOT_FOUND_MESSAGE, meetId));
        }
        meetRepository.deleteById(meetId);
        return ResponseMessage.<Meet>builder().message("Meet deleted successfully ")
                .httpStatus(HttpStatus.OK).build();
    }
}
