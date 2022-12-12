package com.project.schoolmanagment.service;

import com.project.schoolmanagment.entity.abstracts.User;
import com.project.schoolmanagment.entity.concretes.*;
import com.project.schoolmanagment.payload.Dto.TeacherRequestDto;
import com.project.schoolmanagment.payload.request.TeacherRequest;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.payload.response.TeacherResponse;
import com.project.schoolmanagment.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.project.schoolmanagment.utils.Messages;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final LessonProgramService lessonProgramService;
    private final TeacherRequestDto teacherRequestDto;
    private final UserRoleService userRoleService;

    private final AdvisorTeacherService advisorTeacherService;

    public ResponseMessage<TeacherResponse> save(TeacherRequest teacherRequest) {
        if(teacherRepository.existsBySsn(teacherRequest.getSsn().trim())){
            return ResponseMessage.<TeacherResponse>builder().message("This teacher already register").build();
        }
        Set<LessonProgram> lessons = lessonProgramService.getLessonProgramById(teacherRequest.getLessonsIdList());

        Teacher teacher = teacherRequestToDto(teacherRequest, lessons);
        teacher.setUserRole(userRoleService.getUserRole(Role.TEACHER));
        Teacher savedTeacher = teacherRepository.save(teacher);
        if (teacherRequest.isAdvisorTeacher()){
            advisorTeacherService.saveAdvisorTeacher(savedTeacher);
        }
        return ResponseMessage.<TeacherResponse>builder()
                .object(createTeacherResponse(savedTeacher))
                .httpStatus(HttpStatus.CREATED)
                .message("Teacher saved successfully").build();
    }

    public List<Teacher> getAllTeacher() {
        return teacherRepository.findAll();
    }

    public ResponseMessage<TeacherResponse> updateTeacher(TeacherRequest newTeacher, Long userId) {
        Optional<Teacher> teacher = teacherRepository.findById(userId);
        if (teacher.isPresent()) {
            Teacher updateTeacher = createUpdatedTeacher(newTeacher, userId);
            updateTeacher.setLessons(lessonProgramService.getLessonProgramById(newTeacher.getLessonsIdList()));
            updateTeacher.setUserRole(userRoleService.getUserRole(Role.TEACHER));
            Teacher savedTeacher = teacherRepository.save(updateTeacher);
            callAdvisorService(newTeacher.isAdvisorTeacher(),savedTeacher);
            return ResponseMessage.<TeacherResponse>builder()
                    .object(createTeacherResponse(updateTeacher))
                    .httpStatus(HttpStatus.OK)
                    .message("Teacher updated Successful").build();
        }
        return ResponseMessage.<TeacherResponse>builder().message(Messages.NOT_FOUND_USER_MESSAGE).httpStatus(HttpStatus.NOT_FOUND).build();
    }

    public ResponseMessage deleteTeacher(Long id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        ResponseMessage.ResponseMessageBuilder responseMessageBuilder = ResponseMessage.builder();
        if (teacher.isPresent()) {
            teacherRepository.deleteById(id);
            return responseMessageBuilder.message("Teacher Deleted")
                    .httpStatus(HttpStatus.OK)
                    .build();
        }
        return responseMessageBuilder.message(Messages.NOT_FOUND_USER_MESSAGE)
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
    }
    public ResponseMessage<TeacherResponse> getSavedTeacherById(Long id){
        Optional<Teacher> teacher = teacherRepository.findById(id);
        ResponseMessage.ResponseMessageBuilder<TeacherResponse> responseMessageBuilder = ResponseMessage.builder();
        if (teacher.isPresent()){
            return responseMessageBuilder.object(createTeacherResponse(teacher.get()))
                    .httpStatus(HttpStatus.OK)
                    .message("Teacher successfully found").build();
        }
        return responseMessageBuilder.message(Messages.NOT_FOUND_USER_MESSAGE).httpStatus(HttpStatus.NOT_FOUND).build();
    }

    public Teacher getTeacherById(Long id){
        Optional<Teacher> teacher = teacherRepository.findById(id);
        return teacher.orElse(null);
    }

    private Teacher createUpdatedTeacher(TeacherRequest teacher, Long id) {
        return Teacher.builder().id(id)
                .name(teacher.getName())
                .surname(teacher.getSurname())
                .ssn(teacher.getSsn())
                .birthDay(teacher.getBirthDay())
                .birthPlace(teacher.getBirthPlace())
                .password(teacher.getPassword())
                .phoneNumber(teacher.getPhoneNumber())
                .build();
    }

    private Teacher teacherRequestToDto(TeacherRequest teacherRequest, Set<LessonProgram> lessonPrograms) {
        return teacherRequestDto.dtoTeacher(teacherRequest, lessonPrograms);
    }

    public List<Teacher> getTeacherByName(String teacherName) {
        return teacherRepository.getTeacherByNameContaining(teacherName);
    }

    private void callAdvisorService(boolean status,Teacher teacher){
        advisorTeacherService.updateAdvisorTeacher(status,teacher);
    }

    private TeacherResponse createTeacherResponse(Teacher teacher){
        return TeacherResponse.builder().teacherId(teacher.getId())
                .lessonPrograms(teacher.getLessons())
                .name(teacher.getName())
                .surname(teacher.getSurname())
                .ssn(teacher.getSsn())
                .phoneNumber(teacher.getPhoneNumber())
                .build();
    }
}
