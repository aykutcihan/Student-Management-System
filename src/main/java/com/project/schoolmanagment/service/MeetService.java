package com.project.schoolmanagment.service;

import com.project.schoolmanagment.Exception.BadRequestException;
import com.project.schoolmanagment.Exception.ConflictException;
import com.project.schoolmanagment.Exception.InternalServerException;
import com.project.schoolmanagment.Exception.ResourceNotFoundException;
import com.project.schoolmanagment.entity.concretes.AdvisorTeacher;
import com.project.schoolmanagment.entity.concretes.Meet;
import com.project.schoolmanagment.entity.concretes.Student;
import com.project.schoolmanagment.payload.Dto.MeetDto;
import com.project.schoolmanagment.payload.request.MeetRequest;
import com.project.schoolmanagment.payload.response.MeetResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.repository.MeetRepository;
import com.project.schoolmanagment.utils.Messages;
import com.project.schoolmanagment.utils.TimeControl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetService {

    private final MeetRepository meetRepository;
    private final AdvisorTeacherService advisorTeacherService;
    private final StudentService studentService;
    private final MeetDto meetDto;

    public ResponseMessage<MeetResponse> save(MeetRequest meetRequest) {
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
        List<Meet> meets = getAllMeetsByStudentId(student.get().getId());

        checkMeetConflict(meets,meetRequest);

        Meet meet = meetRequestToDto(meetRequest);
        meet.setAdvisorTeacher(advisorTeacher.get());
        meet.setStudent(student.get());
        Meet savedMeet = meetRepository.save(meet);
        sendMailToService(savedMeet,"created a new meeting",student.get().getEmail());
        return ResponseMessage.<MeetResponse>builder().message("Meet Saved Successfully")
                .object(createMeetResponse(savedMeet))
                .httpStatus(HttpStatus.CREATED).build();
    }

    public ResponseMessage<MeetResponse> update(MeetRequest meetRequest,Long meetId) {
        Optional<Meet> getMeet = meetRepository.findById(meetId);
        Optional<Student> student = studentService.getStudentById(meetRequest.getStudentId());
        Optional<AdvisorTeacher> advisorTeacher = advisorTeacherService.getAdvisorTeacherById(meetRequest.getAdvisorTeacherId());
        if(!getMeet.isPresent()){
            throw new ResourceNotFoundException(String.format(Messages.MEET_NOT_FOUND_MESSAGE, meetId));
        }
       else if (!student.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE, meetRequest.getStudentId()));
        } else if (!advisorTeacher.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_ADVISOR_MESSAGE, meetRequest.getAdvisorTeacherId()));
        }
        else if(TimeControl.check(meetRequest.getStartTime(),meetRequest.getStopTime())){
            throw new BadRequestException(Messages.TIME_NOT_VALID_MESSAGE);
        }
        List<Meet> meets = getAllMeetsByStudentId(student.get().getId());
        checkMeetConflict(meets,meetRequest);

        Meet meet = meetRequestToDto(meetRequest);
        meet.setAdvisorTeacher(advisorTeacher.get());
        meet.setStudent(student.get());
        Meet updatedMeet = meetRepository.save(meet);
        sendMailToService(updatedMeet,"updated meet",student.get().getEmail());
        return ResponseMessage.<MeetResponse>builder().message("Meet Updated Successfully")
                .object(createMeetResponse(updatedMeet))
                .httpStatus(HttpStatus.CREATED).build();
    }

    public ResponseMessage<MeetResponse> getMeetById(Long meetId) {
        Optional<Meet> meet = meetRepository.findById(meetId);
        if (!meet.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.MEET_NOT_FOUND_MESSAGE, meetId));
        }
        return ResponseMessage.<MeetResponse>builder().message("Meet successfully found")
                .object(createMeetResponse(meet.get()))
                .httpStatus(HttpStatus.CREATED).build();
    }

    public List<MeetResponse> getAll() {
        return meetRepository.findAll().stream().map(this::createMeetResponse).collect(Collectors.toList());
    }

    public List<MeetResponse> getAllMeetByAdvisorTeacher(Long advisorId) {
        Optional<AdvisorTeacher> advisorTeacher = advisorTeacherService.getAdvisorTeacherById(advisorId);
        if (!advisorTeacher.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_ADVISOR_MESSAGE, advisorId));
        }
        return meetRepository.getAllMeetByAdvisorTeacher_Id(advisorId).stream().map(this::createMeetResponse).collect(Collectors.toList());
    }

    private Meet meetRequestToDto(MeetRequest meetRequest) {
        return meetDto.meetDto(meetRequest);
    }

    public List<MeetResponse> getAllMeetByStudent(Long studentId) {
        Optional<Student> student = studentService.getStudentById(studentId);
        if (!student.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE, studentId));
        }
        return meetRepository.getAllMeetByStudent_Id(studentId).stream().map(this::createMeetResponse).collect(Collectors.toList());
    }

    public ResponseMessage delete(Long meetId) {
        Optional<Meet> meet = meetRepository.findById(meetId);
        if (!meet.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.MEET_NOT_FOUND_MESSAGE, meetId));
        }
        meetRepository.deleteById(meetId);
        try {
            EmailService.sendMail(meet.get().getStudent().getEmail(),
                    "The meeting of the advisor named "+meet.get().getAdvisorTeacher().getTeacher().getName()
                    +" with "+ meet.get().getStudent().getName() +" was canceled. Information of meet is below"
                    +"\n Date "+meet.get().getDate()
                    +"\n Start Time "+meet.get().getDate()
                    +"\n Stop Time "+meet.get().getDate(),"Cancel Meet");
        } catch (Exception e) {
            throw new InternalServerException(e.getMessage());
        }
        return ResponseMessage.<Meet>builder().message("Meet deleted successfully ")
                .httpStatus(HttpStatus.OK).build();
    }
    private MeetResponse createMeetResponse(Meet meet){
       return MeetResponse.builder().id(meet.getId())
                .date(meet.getDate())
                .startTime(meet.getStartTime())
                .stopTime(meet.getStopTime())
                .description(meet.getDescription())
                .advisorTeacherId(meet.getAdvisorTeacher().getId())
                .teacherSsn(meet.getAdvisorTeacher().getTeacher().getSsn())
                .teacherName(meet.getAdvisorTeacher().getTeacher().getName())
                .studentName(meet.getStudent().getName())
                .studentSsn(meet.getStudent().getSsn())
                .studentId(meet.getStudent().getId()).build();
    }
    private void sendMailToService(Meet meet,String message,String studentEmail){
        try {
            EmailService.sendMail(studentEmail," Date "+meet.getDate()
                    +"\n Start Time "+meet.getStartTime()
                    +"\n Stop Time "+meet.getStopTime()
                    +"\n Advisor Teacher Name "+meet.getAdvisorTeacher().getTeacher().getName()
                    +"\n Description "+meet.getDescription(),message);
        } catch (Exception e) {
            throw new InternalServerException(e.getMessage());
        }
    }

    private void checkMeetConflict(List<Meet> meets,MeetRequest meetRequest){
        if(meets.size()>0){
            for(Meet meetOne : meets){
                if(meetOne.getDate().equals(meetRequest.getDate()) && meetOne.getStartTime().equals(meetRequest.getStartTime())){
                    throw new ConflictException(Messages.MEET_EXIST_MESSAGE);
                }
            }
        }
    }
    private List<Meet> getAllMeetsByStudentId(Long studentId){
       return meetRepository.getAllMeetByStudent_Id(studentId);
    }

    public Page<Meet> search(int page, int size, String sort, String type) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }

        return meetRepository.findAll(pageable);
    }
}
