package com.project.schoolmanagment.service;

import com.project.schoolmanagment.Exception.BadRequestException;
import com.project.schoolmanagment.Exception.ResourceNotFoundException;
import com.project.schoolmanagment.entity.concretes.*;
import com.project.schoolmanagment.entity.enums.Role;
import com.project.schoolmanagment.payload.Dto.TeacherRequestDto;
import com.project.schoolmanagment.payload.request.ChooseLessonTeacherRequest;
import com.project.schoolmanagment.payload.request.TeacherRequest;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.payload.response.TeacherResponse;
import com.project.schoolmanagment.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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

    private final CreateResponseObjectService responseObjectService;

    private final PasswordEncoder passwordEncoder;

    public ResponseMessage<TeacherResponse> save(TeacherRequest teacherRequest) {
        //will add control email, phonenumber
        if(teacherRepository.existsBySsn(teacherRequest.getSsn().trim())){
            return ResponseMessage.<TeacherResponse>builder().message("This teacher already register").build();
        }
        Set<LessonProgram> lessons = lessonProgramService.getLessonProgramById(teacherRequest.getLessonsIdList());

        Teacher teacher = teacherRequestToDto(teacherRequest);
        teacher.setUserRole(userRoleService.getUserRole(Role.TEACHER));
        teacher.setLessonsProgramList(lessons);
        teacher.setPassword(passwordEncoder.encode(teacherRequest.getPassword()));
        Teacher savedTeacher = teacherRepository.save(teacher);
        if (teacherRequest.isAdvisorTeacher()){
            advisorTeacherService.saveAdvisorTeacher(savedTeacher);
        }
        return ResponseMessage.<TeacherResponse>builder()
                .object(responseObjectService.createTeacherResponse(savedTeacher))
                .httpStatus(HttpStatus.CREATED)
                .message("Teacher saved successfully").build();
    }

    public List<TeacherResponse> getAllTeacher() {
        return teacherRepository.findAll().stream().map(responseObjectService::createTeacherResponse).collect(Collectors.toList());
    }

    public ResponseMessage<TeacherResponse> updateTeacher(TeacherRequest newTeacher, Long userId) {
        Optional<Teacher> teacher = teacherRepository.findById(userId);
        if (teacher.isPresent()) {
            Teacher updateTeacher = createUpdatedTeacher(newTeacher, userId);
            updateTeacher.setLessonsProgramList(lessonProgramService.getLessonProgramById(newTeacher.getLessonsIdList()));
            updateTeacher.setUserRole(userRoleService.getUserRole(Role.TEACHER));
            Teacher savedTeacher = teacherRepository.save(updateTeacher);
            callAdvisorService(newTeacher.isAdvisorTeacher(),savedTeacher);
            return ResponseMessage.<TeacherResponse>builder()
                    .object(responseObjectService.createTeacherResponse(updateTeacher))
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
            return responseMessageBuilder.object(responseObjectService.createTeacherResponse(teacher.get()))
                    .httpStatus(HttpStatus.OK)
                    .message("Teacher successfully found").build();
        }
        return responseMessageBuilder.message(Messages.NOT_FOUND_USER_MESSAGE).httpStatus(HttpStatus.NOT_FOUND).build();
    }

    public ResponseMessage<Teacher> getTeacherById(Long id){
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if(!teacher.isPresent()){
            throw new BadRequestException(String.format(Messages.NOT_FOUND_USER_MESSAGE, id));
        }
        return ResponseMessage.<Teacher>builder().message("Teacher successfully found")
                .object(teacher.get())
                .httpStatus(HttpStatus.OK).build();
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

    private Teacher teacherRequestToDto(TeacherRequest teacherRequest) {
        return teacherRequestDto.dtoTeacher(teacherRequest);
    }

    public List<TeacherResponse> getTeacherByName(String teacherName) {
        return teacherRepository.getTeacherByNameContaining(teacherName).stream().map(responseObjectService::createTeacherResponse)
                .collect(Collectors.toList());
    }

    public Page<Teacher> search(int page,int size,String sort,String type){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")){
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }

        return teacherRepository.findAll(pageable);
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
        CheckSameLessonProgram.checkLessonPrograms(existLessonProgram,lessonPrograms);
        existLessonProgram.addAll(lessonPrograms);
        teacher.get().setLessonsProgramList(existLessonProgram);
        Teacher savedTeacher = teacherRepository.save(teacher.get());
        return ResponseMessage.<TeacherResponse>builder()
                .message("Lesson added")
                .object(responseObjectService.createTeacherResponse(savedTeacher))
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    private void callAdvisorService(boolean status,Teacher teacher){
        advisorTeacherService.updateAdvisorTeacher(status,teacher);
    }
}
