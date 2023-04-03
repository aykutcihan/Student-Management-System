package com.project.schoolmanagment.service;

import com.project.schoolmanagment.Exception.BadRequestException;
import com.project.schoolmanagment.Exception.ConflictException;
import com.project.schoolmanagment.Exception.ResourceNotFoundException;
import com.project.schoolmanagment.entity.concretes.LessonProgram;
import com.project.schoolmanagment.entity.concretes.Teacher;
import com.project.schoolmanagment.entity.enums.Role;
import com.project.schoolmanagment.payload.Dto.TeacherRequestDto;
import com.project.schoolmanagment.payload.request.ChooseLessonTeacherRequest;
import com.project.schoolmanagment.payload.request.TeacherRequest;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.payload.response.TeacherResponse;
import com.project.schoolmanagment.repository.*;
import com.project.schoolmanagment.service.util.CheckSameLessonProgram;
import com.project.schoolmanagment.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TeacherService {

    private final AdminService adminService;
    private final TeacherRepository teacherRepository;
    private final LessonProgramService lessonProgramService;
    private final TeacherRequestDto teacherRequestDto;
    private final UserRoleService userRoleService;

    private final AdvisorTeacherService advisorTeacherService;

    private final CreateResponseObjectService responseObjectService;

    private final PasswordEncoder passwordEncoder;


    public ResponseMessage<TeacherResponse> save(TeacherRequest teacherRequest) {
        Set<LessonProgram> lessons = lessonProgramService.getLessonProgramById(teacherRequest.getLessonsIdList());
        if (lessons.size() == 0) {
            throw new BadRequestException(Messages.LESSON_PROGRAM_NOT_FOUND_MESSAGE);
        } else
            adminService.checkDuplicateWithEmail(teacherRequest.getUsername(), teacherRequest.getSsn(), teacherRequest.getPhoneNumber(), teacherRequest.getEmail());

        Teacher teacher = teacherRequestToDto(teacherRequest);
        teacher.setUserRole(userRoleService.getUserRole(Role.TEACHER));
        teacher.setLessonsProgramList(lessons);
        teacher.setPassword(passwordEncoder.encode(teacherRequest.getPassword()));
        Teacher savedTeacher = teacherRepository.save(teacher);
        if (teacherRequest.isAdvisorTeacher()) {
            advisorTeacherService.saveAdvisorTeacher(savedTeacher);
        }
        return ResponseMessage.<TeacherResponse>builder()
                .object(responseObjectService.createTeacherResponse(savedTeacher))
                .httpStatus(HttpStatus.CREATED)
                .message("Teacher saved successfully").build();
    }

    public ResponseMessage<TeacherResponse> updateTeacher(TeacherRequest newTeacher, Long userId) {
        Optional<Teacher> teacher = teacherRepository.findById(userId);
        Set<LessonProgram> lessons = lessonProgramService.getLessonProgramById(newTeacher.getLessonsIdList());
        if (!teacher.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE, userId));
        } else if (lessons.size() == 0) {
            throw new BadRequestException(Messages.LESSON_PROGRAM_NOT_FOUND_MESSAGE);
        } else if (!checkParameterForUpdateMethod(teacher.get(), newTeacher)) {
            adminService.checkDuplicateWithEmail(newTeacher.getUsername(), newTeacher.getSsn(), newTeacher.getPhoneNumber(), newTeacher.getEmail());

        }

        Teacher updateTeacher = createUpdatedTeacher(newTeacher, userId);
        updateTeacher.setLessonsProgramList(lessonProgramService.getLessonProgramById(newTeacher.getLessonsIdList()));
        updateTeacher.setPassword(passwordEncoder.encode(newTeacher.getPassword()));
        Teacher savedTeacher = teacherRepository.save(updateTeacher);
        callAdvisorService(newTeacher.isAdvisorTeacher(), savedTeacher);
        return ResponseMessage.<TeacherResponse>builder()
                .object(responseObjectService.createTeacherResponse(updateTeacher))
                .httpStatus(HttpStatus.OK)
                .message("Teacher updated Successful").build();
    }


    public ResponseMessage deleteTeacher(Long id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if (!teacher.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE, id));
        }
        teacherRepository.deleteById(id);
        return ResponseMessage.builder().message("Teacher Deleted")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public List<TeacherResponse> getAllTeacher() {
        return teacherRepository.findAll().stream().map(responseObjectService::createTeacherResponse).collect(Collectors.toList());
    }

    public ResponseMessage<TeacherResponse> getSavedTeacherById(Long id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if (!teacher.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE, id));
        }
        return ResponseMessage.<TeacherResponse>builder()
                .object(responseObjectService.createTeacherResponse(teacher.get()))
                .httpStatus(HttpStatus.OK)
                .message("Teacher successfully found").build();
    }

    public Optional<Teacher> getTeacherById(Long id) {
        return teacherRepository.findById(id);
    }

    private Teacher createUpdatedTeacher(TeacherRequest teacher, Long id) {
        return Teacher.builder().id(id)
                .username(teacher.getUsername())
                .name(teacher.getName())
                .surname(teacher.getSurname())
                .ssn(teacher.getSsn())
                .birthDay(teacher.getBirthDay())
                .birthPlace(teacher.getBirthPlace())
                .phoneNumber(teacher.getPhoneNumber())
                .isAdvisor(teacher.isAdvisorTeacher())
                .userRole(userRoleService.getUserRole(Role.TEACHER))
                .gender(teacher.getGender())
                .email(teacher.getEmail())
                .build();
    }

    private Teacher teacherRequestToDto(TeacherRequest teacherRequest) {
        return teacherRequestDto.dtoTeacher(teacherRequest);
    }

    public List<TeacherResponse> getTeacherByName(String teacherName) {
        return teacherRepository.getTeacherByNameContaining(teacherName).stream().map(responseObjectService::createTeacherResponse)
                .collect(Collectors.toList());
    }

    public Page<TeacherResponse> search(int page, int size, String sort, String type) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }

        return teacherRepository.findAll(pageable).map(responseObjectService::createTeacherResponse);
    }

    public ResponseMessage<TeacherResponse> chooseLesson(ChooseLessonTeacherRequest chooseLessonRequest) {
        Optional<Teacher> teacher = teacherRepository.findById(chooseLessonRequest.getTeacherId());
        Set<LessonProgram> lessonPrograms = lessonProgramService.getLessonProgramById(chooseLessonRequest.getLessonProgramId());
        if (!teacher.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE, chooseLessonRequest.getTeacherId()));
        } else if (lessonPrograms.size() == 0) {
            throw new ResourceNotFoundException(Messages.LESSON_PROGRAM_NOT_FOUND_MESSAGE);
        }
        Set<LessonProgram> existLessonProgram = teacher.get().getLessonsProgramList();
        CheckSameLessonProgram.checkLessonPrograms(existLessonProgram, lessonPrograms);
        existLessonProgram.addAll(lessonPrograms);
        teacher.get().setLessonsProgramList(existLessonProgram);
        Teacher savedTeacher = teacherRepository.save(teacher.get());
        return ResponseMessage.<TeacherResponse>builder()
                .message("Lesson added to Teacher")
                .object(responseObjectService.createTeacherResponse(savedTeacher))
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    private void callAdvisorService(boolean status, Teacher teacher) {
        advisorTeacherService.updateAdvisorTeacher(status, teacher);
    }

    private boolean checkParameterForUpdateMethod(Teacher teacher, TeacherRequest newTeacherRequest) {
        return teacher.getSsn().equalsIgnoreCase(newTeacherRequest.getSsn())
                || teacher.getUsername().equalsIgnoreCase(newTeacherRequest.getUsername())
                || teacher.getPhoneNumber().equalsIgnoreCase(newTeacherRequest.getPhoneNumber())
                || teacher.getEmail().equalsIgnoreCase(newTeacherRequest.getEmail());
    }

    public Teacher getTeacherByUsername(String username) {
        if (!teacherRepository.existsByUsername(username))
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE, username));
        return teacherRepository.getTeacherByUsername(username);
    }


}
