package com.project.schoolmanagment.service;

import com.project.schoolmanagment.Exception.BadRequestException;
import com.project.schoolmanagment.Exception.ConflictException;
import com.project.schoolmanagment.Exception.InternalServerException;
import com.project.schoolmanagment.Exception.ResourceNotFoundException;
import com.project.schoolmanagment.entity.concretes.AdvisorTeacher;
import com.project.schoolmanagment.entity.concretes.Meet;
import com.project.schoolmanagment.entity.concretes.Student;
import com.project.schoolmanagment.payload.request.MeetRequestWithoutId;
import com.project.schoolmanagment.payload.request.UpdateRequest.UpdateMeetRequest;
import com.project.schoolmanagment.payload.response.MeetResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.repository.MeetRepository;
import com.project.schoolmanagment.repository.StudentRepository;
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
    private final StudentRepository studentRepository;

    public ResponseMessage<MeetResponse> save(String username, MeetRequestWithoutId meetRequest) {

        Optional<AdvisorTeacher> advisorTeacher = advisorTeacherService.getAdvisorTeacherByUsername(username);
        if (!advisorTeacher.isPresent())
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_ADVISOR_MESSAGE, username));

        if (TimeControl.check(meetRequest.getStartTime(), meetRequest.getStopTime()))
            throw new BadRequestException(Messages.TIME_NOT_VALID_MESSAGE);

        for (Long studentId : meetRequest.getStudentIds()) {
            boolean check = studentRepository.existsByIdEquals(studentId);
            if (!check) throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE_WITH_ID, studentId));
            checkMeetConflict(studentId, meetRequest.getDate(), meetRequest.getStartTime());
        }

        List<Student> students = studentService.getStudentByIds(meetRequest.getStudentIds());
        Meet meet = new Meet();
        meet.setDate(meetRequest.getDate());
        meet.setStartTime(meetRequest.getStartTime());
        meet.setStopTime(meetRequest.getStopTime());
        meet.setStudentList(students);
        meet.setDescription(meetRequest.getDescription());
        meet.setAdvisorTeacher(advisorTeacher.get());
        Meet savedMeet = meetRepository.save(meet);
       // sendMailToService(savedMeet, "created a new meeting", students);

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
        for (Long studentId : meetRequest.getStudentIds())
            checkMeetConflict(studentId, meetRequest.getDate(), meetRequest.getStartTime());
        List<Student> students = studentService.getStudentByIds(meetRequest.getStudentIds());
        Meet meet = createUpdatedMeet(meetRequest, meetId);
        meet.setStudentList(students);
        meet.setAdvisorTeacher(getMeet.get().getAdvisorTeacher());
        Meet updatedMeet = meetRepository.save(meet);
      //  sendMailToService(updatedMeet, "updated meet", students);
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
        return meetRepository.findAll()
                .stream()
                .map(this::createMeetResponse)
                .collect(Collectors.toList());
    }

    public Page<MeetResponse> getAllMeetByAdvisorTeacherAsPage(String username, Pageable pageable) {
        Optional<AdvisorTeacher> advisorTeacher = advisorTeacherService.getAdvisorTeacherByUsername(username);
        if (!advisorTeacher.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_ADVISOR_MESSAGE, username));
        }
        return  meetRepository.findByAdvisorTeacher_IdEquals( advisorTeacher.get().getId(),pageable).map(this::createMeetResponse);
    }

    public List<MeetResponse> getAllMeetByAdvisorTeacherAsList(String username) {
        Optional<AdvisorTeacher> advisorTeacher = advisorTeacherService.getAdvisorTeacherByUsername(username);
        if (!advisorTeacher.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_ADVISOR_MESSAGE, username));
        }
        return meetRepository.getByAdvisorTeacher_IdEquals(advisorTeacher.get().getId()).stream().map(this::createMeetResponse).collect(Collectors.toList());
    }



    public ResponseMessage delete(Long meetId) {
        Optional<Meet> meet = meetRepository.findById(meetId);
        if (!meet.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.MEET_NOT_FOUND_MESSAGE, meetId));
        }
        meetRepository.deleteById(meetId);
        List<Student> students = studentRepository.findByMeetList_IdEquals(meetId);
        try {
            for (Student student : students)
                EmailService.sendMail(student.getEmail(),
                        "The meeting of the advisor named " + meet.get().getAdvisorTeacher().getTeacher().getName()
                                + " with " + student + " was canceled. Information of meet is below"
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
                .students(meet.getStudentList())
                .build();
    }

    private void sendMailToService(Meet meet, String message, List<Student> students) {
        try {
            for (Student student : students)
                EmailService.sendMail(student.getEmail(), " Date " + meet.getDate()
                        + "\n Start Time " + meet.getStartTime()
                        + "\n Stop Time " + meet.getStopTime()
                        + "\n Advisor Teacher Name " + meet.getAdvisorTeacher().getTeacher().getName()
                        + "\n Description " + meet.getDescription(), message);
        } catch (Exception e) {
            throw new InternalServerException(e.getMessage());
        }
    }

    private void checkMeetConflict(Long studentId, LocalDate date, LocalTime startTime) {
        List<Meet> meets = meetRepository.findByStudentList_IdEquals(studentId);
        if (meets.size() > 0) {
            for (Meet meetOne : meets) {
                if (meetOne.getDate().equals(date) && meetOne.getStartTime().equals(startTime)) {
                    throw new ConflictException(Messages.MEET_EXIST_MESSAGE);
                }
            }
        }
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

    public List<MeetResponse> getAllMeetByStudentByUsername(String username) {
        Optional<Student> student = studentService.getStudentByUsernameForOptional(username);
        if (!student.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE, username));
        }
        return meetRepository.findByStudentList_IdEquals(student.get().getId()).stream().map(this::createMeetResponse).collect(Collectors.toList());

    }
}
