package com.project.schoolmanagment.service;

import com.project.schoolmanagment.Exception.BadRequestException;
import com.project.schoolmanagment.Exception.ResourceNotFoundException;
import com.project.schoolmanagment.entity.concretes.*;
import com.project.schoolmanagment.entity.enums.Role;
import com.project.schoolmanagment.payload.Dto.StudentRequestDto;
import com.project.schoolmanagment.payload.request.ChooseLessonRequest;
import com.project.schoolmanagment.payload.request.StudentRequest;
import com.project.schoolmanagment.payload.response.*;
import com.project.schoolmanagment.repository.StudentRepository;
import com.project.schoolmanagment.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentRequestDto studentRequestDto;

    private final UserRoleService userRoleService;

    private final AdvisorTeacherService advisorTeacherService;
    private final LessonService lessonService;

    private final CreateResponseObjectService responseObjectService;
    private final LessonProgramService lessonProgramService;

    private final PasswordEncoder passwordEncoder;

    //telefon pattern düzenlenecek
    public ResponseMessage<StudentResponse> save(StudentRequest studentRequest) {
        Optional<AdvisorTeacher> advisorTeacher = advisorTeacherService.getAdvisorTeacherById(studentRequest.getAdvisorTeacherId());
        ResponseMessage.ResponseMessageBuilder<StudentResponse> responseMessageBuilder = ResponseMessage.builder();
        if (studentRepository.existsBySsn(studentRequest.getSsn())) {
            throw new BadRequestException(String.format(Messages.ALREADY_REGISTER_MESSAGE_SSN, studentRequest.getSsn()));
        } else if (!advisorTeacher.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_ADVISOR_MESSAGE, studentRequest.getAdvisorTeacherId()));
        } else if (studentRepository.existsByStudentNumber(studentRequest.getStudentNumber())) {
            throw new BadRequestException(String.format(Messages.ALREADY_REGISTER_MESSAGE_STUDENT_NUMBER, studentRequest.getStudentNumber()));
        }
        Student student = studentRequestToDto(studentRequest);
        //student.setParent(); from parent service
        student.setAdvisorTeacher(advisorTeacher.get());
        student.setUserRole(userRoleService.getUserRole(Role.STUDENT));
        student.setPassword(passwordEncoder.encode(studentRequest.getPassword()));
        return responseMessageBuilder.object(responseObjectService.createStudentResponse(studentRepository.save(student)))
                .message("Student saved Successfully").build();
    }

    public List<StudentResponse> getAllStudent() {
        return studentRepository.findAll().stream().map(responseObjectService::createStudentResponse).collect(Collectors.toList());
    }

    public ResponseMessage<StudentResponse> updateStudent(Long userId, StudentRequest studentRequest) {
        Optional<Student> student = studentRepository.findById(userId);
        Optional<AdvisorTeacher> advisorTeacher = advisorTeacherService.getAdvisorTeacherById(studentRequest.getAdvisorTeacherId());
        ResponseMessage.ResponseMessageBuilder<StudentResponse> responseMessageBuilder = ResponseMessage.builder();
        if (!student.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE, userId));
        } else if (studentRepository.existsBySsn(studentRequest.getSsn())) {
            throw new BadRequestException(String.format(Messages.ALREADY_REGISTER_MESSAGE_SSN, studentRequest.getSsn()));
        } else if (!advisorTeacher.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_ADVISOR_MESSAGE, studentRequest.getAdvisorTeacherId()));
        } else if (studentRepository.existsByStudentNumber(studentRequest.getStudentNumber())) {
            throw new BadRequestException(String.format(Messages.ALREADY_REGISTER_MESSAGE_STUDENT_NUMBER, studentRequest.getStudentNumber()));
        }
        Student updateStudent = createUpdatedStudent(studentRequest, userId);
        updateStudent.setUserRole(userRoleService.getUserRole(Role.STUDENT));
        studentRepository.save(updateStudent);
        return responseMessageBuilder.object(responseObjectService.createStudentResponse(updateStudent))
                .message("Teacher updated Successfully").build();

    }

    public ResponseMessage deleteStudent(Long studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (student.isPresent()) {
            studentRepository.deleteById(studentId);
            return ResponseMessage.builder().message("Student Deleted")
                    .httpStatus(HttpStatus.OK)
                    .build();
        }
        throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE, studentId));
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
                .phoneNumber(studentRequest.getPhoneNumber())
                .build();
    }


    public ResponseMessage<StudentResponse> chooseLesson(ChooseLessonRequest chooseLessonRequest) {
        Optional<Student> student = studentRepository.findById(chooseLessonRequest.getStudentId());
        Set<LessonProgram> lessonPrograms = lessonProgramService.getLessonProgramById(chooseLessonRequest.getLessonProgramId());
        if (!student.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE, chooseLessonRequest.getStudentId()));
        } else if (lessonPrograms.size() == 0) {
            throw new ResourceNotFoundException(Messages.LESSON_PROGRAM_NOT_FOUND_MESSAGE);
        }
        Set<LessonProgram> studentLessonProgram = student.get().getLessonsProgramList();

        CheckSameLessonProgram.checkLessonPrograms(studentLessonProgram,lessonPrograms);

        studentLessonProgram.addAll(lessonPrograms);
        student.get().setLessonsProgramList(studentLessonProgram);
        Student savedStudent = studentRepository.save(student.get());
        return ResponseMessage.<StudentResponse>builder()
                .message("Lesson added")
                .object(responseObjectService.createStudentResponse(savedStudent))
                .httpStatus(HttpStatus.CREATED)
                .build();

    }
    public Optional<Student> getStudentById(Long studentId){
        return studentRepository.findById(studentId);
    }

    public List<StudentResponse> getAllStudentByAdvisorId(Long advisorId) {
        return studentRepository.getStudentByAdvisorTeacher_Id(advisorId).stream().map(responseObjectService::createStudentResponse)
                .collect(Collectors.toList());
    }

    public List<StudentResponse> getStudentByName(String studentName) {
        return studentRepository.getStudentByNameContaining(studentName).stream().map(responseObjectService::createStudentResponse)
                .collect(Collectors.toList());
    }

    public Set<Student> getStudentsByIdList(Set<Long> idList) {
        return studentRepository.getStudentsByStudentIdList(idList);
    }

    public Optional<AdvisorTeacher> getAdvisorTeacherById(Long userId) {
        return advisorTeacherService.getAdvisorTeacherById(userId);
    }

    public Page<Student> search(int page, int size, String sort, String type) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }

        return studentRepository.findAll(pageable);
    }
}
