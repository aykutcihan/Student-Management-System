package com.project.schoolmanagment.service;

import com.project.schoolmanagment.entity.concretes.*;
import com.project.schoolmanagment.payload.Dto.StudentRequestDto;
import com.project.schoolmanagment.payload.request.StudentRequest;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.repository.StudentRepository;
import com.project.schoolmanagment.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentRequestDto studentRequestDto;

    private final UserRoleService userRoleService;

    private final AdvisorTeacherService advisorTeacherService;
    private final LessonService lessonService;

    public ResponseMessage<Student> save(StudentRequest studentRequest) {
        AdvisorTeacher advisorTeacher = advisorTeacherService.getAdvisorTeacherById(studentRequest.getAdvisorTeacherId());
        if (studentRepository.existsBySsn(studentRequest.getSsn())) {
            return ResponseMessage.<Student>builder()
                    .message("This student already register").build();
        }
        if(advisorTeacher==null){
            return ResponseMessage.<Student>builder()
                    .message("Not found Advisor teacher").build();
        }

        //Set<Lesson> lessons = getLessonsByLessonId(studentRequest.getLessonIdList());
        Student student = studentRequestToDto(studentRequest);
        //student.setLessons(lessons);
        //student.setParent(); from parent service
        student.setAdvisorTeacher(advisorTeacher);
        return ResponseMessage.<Student>builder().object(studentRepository.save(student))
                .message("Student saved successfully").build();
    }

    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    public ResponseMessage<Student> updateStudent(Long userId, StudentRequest studentRequest) {
        Optional<Student> student = studentRepository.findById(userId);
        if (student.isPresent()) {
            Student updateStudent = createUpdatedStudent(studentRequest, userId);
            //updateStudent.setLessons(getLessonsByLessonId(studentRequest.getLessonIdList()));
            updateStudent.setUserRole(userRoleService.getUserRole(Role.STUDENT));
            studentRepository.save(updateStudent);
            return ResponseMessage.<Student>builder().object(updateStudent).message("Teacher updated Successful").build();
        }
        return ResponseMessage.<Student>builder().message(Messages.NOT_FOUND_USER_MESSAGE).build();
    }

    public String deleteStudent(Long studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (student.isPresent()) {
            studentRepository.deleteById(studentId);
            return "Teacher deleted Successful";
        }
        return Messages.NOT_FOUND_USER_MESSAGE;
    }
    private Student studentRequestToDto(StudentRequest studentRequest) {
        return studentRequestDto.dtoStudent(studentRequest);
    }

    private Set<Lesson> getLessonsByLessonId(Set<Long> idList) {
        return lessonService.getLessonByLessonNameList(idList);
    }

    private Student createUpdatedStudent(StudentRequest studentRequest, Long userId) {
        return Student.builder().id(userId)
                .name(studentRequest.getName())
                .surname(studentRequest.getSurname())
                .ssn(studentRequest.getSsn())
                .birthDay(studentRequest.getBirthDay())
                .birthPlace(studentRequest.getBirthPlace())
                .password(studentRequest.getPassword())
                .motherName(studentRequest.getMotherName())
                .fatherName(studentRequest.getFatherName())
                .studentNumber(studentRequest.getStudentNumber())
                .build();
    }

    public List<Student> getStudentByName(String studentName) {
        return studentRepository.getStudentByNameContaining(studentName);
    }
}
