package com.project.schoolmanagment.service;

import com.project.schoolmanagment.entity.abstracts.User;
import com.project.schoolmanagment.entity.concretes.Lesson;
import com.project.schoolmanagment.entity.concretes.Role;
import com.project.schoolmanagment.entity.concretes.Teacher;
import com.project.schoolmanagment.entity.concretes.UserRole;
import com.project.schoolmanagment.payload.Dto.TeacherRequestDto;
import com.project.schoolmanagment.payload.request.TeacherRequest;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.project.schoolmanagment.utils.Messages;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final LessonService lessonService;
    private final TeacherRequestDto teacherRequestDto;
    private final UserRoleService userRoleService;

    public ResponseMessage<Teacher> save(TeacherRequest teacherRequest) {
        if(teacherRepository.existsBySsn(teacherRequest.getSsn().trim())){
            return ResponseMessage.<Teacher>builder().message("This teacher already register").build();
        }
        Set<Lesson> lessons = getLessonsByLessonId(teacherRequest.getLessons());
        if (lessons.size() == 0) {
            return ResponseMessage.<Teacher>builder().message("lessons must not empty").build();
        }

        Teacher teacher = teacherRequestToDto(teacherRequest, lessons);
        teacher.setUserRole(userRoleService.getUserRole(Role.TEACHER));
        return ResponseMessage.<Teacher>builder().object(teacherRepository.save(teacher))
                .message("teacher saved successfully").build();
    }

    public List<Teacher> getAllTeacher() {
        return teacherRepository.findAll();
    }

    public ResponseMessage<Teacher> updateTeacher(Teacher newTeacher, Long userId) {
        Optional<Teacher> teacher = teacherRepository.findById(userId);
        if (teacher.isPresent()) {
            Teacher updateTeacher = createUpdatedTeacher(newTeacher, userId);
            teacherRepository.save(updateTeacher);
            return ResponseMessage.<Teacher>builder().message("Teacher updated Successful").build();
        }
        return ResponseMessage.<Teacher>builder().message(Messages.NOT_FOUND_USER_MESSAGE).build();
    }

    public String deleteTeacher(Long id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if (teacher.isPresent()) {
            teacherRepository.deleteById(id);
            return "Teacher deleted Successful";
        }
        return Messages.NOT_FOUND_USER_MESSAGE;
    }

    private Teacher createUpdatedTeacher(Teacher teacher, Long id) {
        return Teacher.builder().id(id)
                .name(teacher.getName())
                .surname(teacher.getSurname())
                .ssn(teacher.getSsn())
                .birthDay(teacher.getBirthDay())
                .birthPlace(teacher.getBirthPlace())
                .password(teacher.getPassword())
                .lessons(teacher.getLessons()).build();
    }

    private Set<Lesson> getLessonsByLessonId(Set<Long> idList) {
        return lessonService.getLessonByLessonNameList(idList);
    }

    private Teacher teacherRequestToDto(TeacherRequest teacherRequest, Set<Lesson> lessons) {
        return teacherRequestDto.dtoTeacher(teacherRequest, lessons);
    }
}
