package com.project.schoolmanagment.service;

import com.project.schoolmanagment.Exception.BadRequestException;
import com.project.schoolmanagment.Exception.ConflictException;
import com.project.schoolmanagment.Exception.InternalServerException;
import com.project.schoolmanagment.Exception.ResourceNotFoundException;
import com.project.schoolmanagment.entity.concretes.AdvisorTeacher;
import com.project.schoolmanagment.entity.concretes.Meet;
import com.project.schoolmanagment.entity.concretes.Student;
import com.project.schoolmanagment.payload.Dto.MeetDto;
import com.project.schoolmanagment.payload.request.MeetRequestWithoutId;
import com.project.schoolmanagment.payload.request.UpdateRequest.UpdateMeetRequest;
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

import java.time.LocalDate;
import java.time.LocalTime;
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
    private final MeetDto meetDtoObject;

    public ResponseMessage<MeetResponse> save(String ssn, MeetRequestWithoutId meetRequest) {
        Optional<Student> student = studentService.getStudentById(meetRequest.getStudentId());
        Optional<AdvisorTeacher> advisorTeacher = advisorTeacherService.getAdvisorTeacherBySsn(ssn);
        if (!student.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE, meetRequest.getStudentId()));
        } else if (!advisorTeacher.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_ADVISOR_MESSAGE, ssn));
        } else if (TimeControl.check(meetRequest.getStartTime(), meetRequest.getStopTime())) {
            throw new BadRequestException(Messages.TIME_NOT_VALID_MESSAGE);
        }
        List<Meet> meets = getAllMeetsByStudentId(student.get().getId());

        checkMeetConflict(meets, meetRequest.getDate(), meetRequest.getStartTime());

        Meet meet = meetRequestWithoutIdToDto(meetRequest);
        meet.setAdvisorTeacher(advisorTeacher.get());
        meet.setStudent(student.get());
        Meet savedMeet = meetRepository.save(meet);
        sendMailToService(savedMeet, "created a new meeting", student.get().getEmail());
        return ResponseMessage.<MeetResponse>builder().message("Meet Saved Successfully")
                .object(createMeetResponse(savedMeet))
                .httpStatus(HttpStatus.CREATED).build();
    }

    public ResponseMessage<MeetResponse> update(UpdateMeetRequest meetRequest, Long meetId) {
        Optional<Meet> getMeet = meetRepository.findById(meetId);
        if (!getMeet.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.MEET_NOT_FOUND_MESSAGE, meetId));
        } else if (TimeControl.check(meetRequest.getStartTime(), meetRequest.getStopTime())) {
            throw new BadRequestException(Messages.TIME_NOT_VALID_MESSAGE);
        }
        List<Meet> meets = getAllMeetsByStudentId(getMeet.get().getStudent().getId());
        checkMeetConflict(meets, meetRequest.getDate(), meetRequest.getStartTime());

        Meet meet = createUpdatedMeet(meetRequest, meetId);
        meet.setAdvisorTeacher(getMeet.get().getAdvisorTeacher());
        meet.setStudent(getMeet.get().getStudent());
        Meet updatedMeet = meetRepository.save(meet);
        sendMailToService(updatedMeet, "updated meet", meet.getStudent().getEmail());
        return ResponseMessage.<MeetResponse>builder().message("Meet Updated Successfully")
                .object(createMeetResponse(updatedMeet))
                .httpStatus(HttpStatus.OK).build();
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

    public Page<Meet> getAllMeetByAdvisorTeacherAsPage(Pageable pageable, String ssn) {
        Optional<AdvisorTeacher> advisorTeacher = advisorTeacherService.getAdvisorTeacherBySsn(ssn);
        if (!advisorTeacher.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_ADVISOR_MESSAGE, ssn));
        }
        return meetRepository.findByAdvisorTeacher_Teacher_SsnEquals(pageable, ssn);
    }

    public List<Meet> getAllMeetByAdvisorTeacherAsList(String ssn) {
        Optional<AdvisorTeacher> advisorTeacher = advisorTeacherService.getAdvisorTeacherBySsn(ssn);
        if (!advisorTeacher.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_ADVISOR_MESSAGE, ssn));
        }
        return meetRepository.findByAdvisorTeacher_Teacher_SsnEqualsAsList(ssn);
    }

    private Meet meetRequestWithoutIdToDto(MeetRequestWithoutId meetRequestWithoutId) {
        return meetDtoObject.meetDto(meetRequestWithoutId);
    }


    public ResponseMessage delete(Long meetId) {
        Optional<Meet> meet = meetRepository.findById(meetId);
        if (!meet.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.MEET_NOT_FOUND_MESSAGE, meetId));
        }
        meetRepository.deleteById(meetId);
        try {
            EmailService.sendMail(meet.get().getStudent().getEmail(),
                    "The meeting of the advisor named " + meet.get().getAdvisorTeacher().getTeacher().getName()
                            + " with " + meet.get().getStudent().getName() + " was canceled. Information of meet is below"
                            + "\n Date " + meet.get().getDate()
                            + "\n Start Time " + meet.get().getDate()
                            + "\n Stop Time " + meet.get().getDate(), "Cancel Meet");
        } catch (Exception e) {
            throw new InternalServerException(e.getMessage());
        }
        return ResponseMessage.<Meet>builder().message("Meet deleted successfully ")
                .httpStatus(HttpStatus.OK).build();
    }

    private MeetResponse createMeetResponse(Meet meet) {
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

    private void sendMailToService(Meet meet, String message, String studentEmail) {
        try {
            EmailService.sendMail(studentEmail, " Date " + meet.getDate()
                    + "\n Start Time " + meet.getStartTime()
                    + "\n Stop Time " + meet.getStopTime()
                    + "\n Advisor Teacher Name " + meet.getAdvisorTeacher().getTeacher().getName()
                    + "\n Description " + meet.getDescription(), message);
        } catch (Exception e) {
            throw new InternalServerException(e.getMessage());
        }
    }

    private void checkMeetConflict(List<Meet> meets, LocalDate date, LocalTime startTime) {
        if (meets.size() > 0) {
            for (Meet meetOne : meets) {
                if (meetOne.getDate().equals(date) && meetOne.getStartTime().equals(startTime)) {
                    throw new ConflictException(Messages.MEET_EXIST_MESSAGE);
                }
            }
        }
    }

    private List<Meet> getAllMeetsByStudentId(Long studentId) {
        return meetRepository.getAllMeetByStudent_Id(studentId);
    }

    public Page<MeetResponse> search(int page, int size, String sort, String type) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }

        return meetRepository.findAll(pageable).map(this::createMeetResponse);
    }

    private Meet createUpdatedMeet(UpdateMeetRequest updateMeetRequest, Long id) {
        return Meet.builder().id(id)
                .startTime(updateMeetRequest.getStartTime())
                .stopTime(updateMeetRequest.getStopTime())
                .date(updateMeetRequest.getDate())
                .description(updateMeetRequest.getDescription())
                .build();
    }

    public List<MeetResponse> getAllMeetByStudentBySsn(String ssn) {

        Optional<Student> student = studentService.getStudentBySnnForOptional(ssn);
        if (!student.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE, ssn));
        }
        return meetRepository.getAllMeetByStudent_Id(student.get().getId()).stream().map(this::createMeetResponse).collect(Collectors.toList());
    }

}
