package com.project.schoolmanagment.service;

import com.project.schoolmanagment.Exception.ConflictException;
import com.project.schoolmanagment.Exception.ResourceNotFoundException;
import com.project.schoolmanagment.entity.concretes.AdvisorTeacher;
import com.project.schoolmanagment.entity.concretes.LessonProgram;
import com.project.schoolmanagment.entity.concretes.Student;
import com.project.schoolmanagment.entity.enums.Role;
import com.project.schoolmanagment.payload.Dto.StudentRequestDto;
import com.project.schoolmanagment.payload.request.ChooseLessonRequest;
import com.project.schoolmanagment.payload.request.ChooseLessonRequestWithoutId;
import com.project.schoolmanagment.payload.request.StudentRequest;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.payload.response.StudentResponse;
import com.project.schoolmanagment.repository.StudentRepository;
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
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentRequestDto studentRequestDto;

    private final UserRoleService userRoleService;

    private final AdvisorTeacherService advisorTeacherService;
    private final LessonService lessonService;

    private final CreateResponseObjectService responseObjectService;
    private final LessonProgramService lessonProgramService;

    private final PasswordEncoder passwordEncoder;

    public ResponseMessage<StudentResponse> save(StudentRequest studentRequest) {
        Optional<AdvisorTeacher> advisorTeacher = advisorTeacherService.getAdvisorTeacherById(studentRequest.getAdvisorTeacherId());
        ResponseMessage.ResponseMessageBuilder<StudentResponse> responseMessageBuilder = ResponseMessage.builder();
        if (studentRepository.existsBySsn(studentRequest.getSsn())) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_SSN, studentRequest.getSsn()));
        } else if (!advisorTeacher.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_ADVISOR_MESSAGE, studentRequest.getAdvisorTeacherId()));
        } else if (studentRepository.existsByStudentNumber(studentRequest.getStudentNumber())) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_STUDENT_NUMBER, studentRequest.getStudentNumber()));
        } else if (studentRepository.existsByEmail(studentRequest.getEmail())) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_EMAIL, studentRequest.getEmail()));
        } else if (studentRepository.existsByPhoneNumber(studentRequest.getPhoneNumber())) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_PHONE_NUMBER, studentRequest.getPhoneNumber()));
        }

        Student student = studentRequestToDto(studentRequest);
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
        } else if (!checkParameterForUpdateMethod(student.get(), studentRequest)) {
            if (studentRepository.existsBySsn(studentRequest.getSsn())) {
                throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_SSN, studentRequest.getSsn()));
            } else if (!advisorTeacher.isPresent()) {
                throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_ADVISOR_MESSAGE, studentRequest.getAdvisorTeacherId()));
            } else if (studentRepository.existsByStudentNumber(studentRequest.getStudentNumber())) {
                throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_STUDENT_NUMBER, studentRequest.getStudentNumber()));
            } else if (studentRepository.existsByEmail(studentRequest.getEmail())) {
                throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_EMAIL, studentRequest.getEmail()));
            }
        }
        Student updateStudent = createUpdatedStudent(studentRequest, userId);
        updateStudent.setPassword(passwordEncoder.encode(studentRequest.getPassword()));
        studentRepository.save(updateStudent);
        return responseMessageBuilder.object(responseObjectService.createStudentResponse(updateStudent))
                .message("Student updated Successfully").build();

    }

    public ResponseMessage deleteStudent(Long studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (!student.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE, studentId));
        }
        studentRepository.deleteById(studentId);
        return ResponseMessage.builder().message("Student Deleted")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    private Student studentRequestToDto(StudentRequest studentRequest) {
        return studentRequestDto.dtoStudent(studentRequest);
    }

    private Student createUpdatedStudent(StudentRequest studentRequest, Long userId) {
        return Student.builder().id(userId)
                .name(studentRequest.getName())
                .surname(studentRequest.getSurname())
                .ssn(studentRequest.getSsn())
                .birthDay(studentRequest.getBirthDay())
                .birthPlace(studentRequest.getBirthPlace())
                .motherName(studentRequest.getMotherName())
                .fatherName(studentRequest.getFatherName())
                .studentNumber(studentRequest.getStudentNumber())
                .phoneNumber(studentRequest.getPhoneNumber())
                .gender(studentRequest.getGender())
                .email(studentRequest.getEmail())
                .userRole(userRoleService.getUserRole(Role.STUDENT))
                .build();
    }


    public ResponseMessage<StudentResponse> chooseLesson(String ssn,ChooseLessonRequestWithoutId chooseLessonRequest) {
        Optional<Student> student = studentRepository.getStudentBySnnForOptional(ssn);
        Set<LessonProgram> lessonPrograms = lessonProgramService.getLessonProgramById(chooseLessonRequest.getLessonProgramId());
        if (!student.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE, ssn));
        } else if (lessonPrograms.size() == 0) {
            throw new ResourceNotFoundException(Messages.LESSON_PROGRAM_NOT_FOUND_MESSAGE);
        }
        Set<LessonProgram> studentLessonProgram = student.get().getLessonsProgramList();

        CheckSameLessonProgram.checkLessonPrograms(studentLessonProgram, lessonPrograms);

        studentLessonProgram.addAll(lessonPrograms);
        student.get().setLessonsProgramList(studentLessonProgram);
        Student savedStudent = studentRepository.save(student.get());
        return ResponseMessage.<StudentResponse>builder()
                .message("Lesson added to Student")
                .object(responseObjectService.createStudentResponse(savedStudent))
                .httpStatus(HttpStatus.CREATED)
                .build();

    }

    public Optional<Student> getStudentById(Long studentId) {
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

    public StudentResponse getStudentBySnn(String ssn) {
        return studentRepository.getStudentBySsn(ssn);
    }

    public Page<StudentResponse> search(int page, int size, String sort, String type) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }

        return studentRepository.findAll(pageable).map(responseObjectService::createStudentResponse);
    }

    private boolean checkParameterForUpdateMethod(Student student, StudentRequest newStudentRequest) {
        return student.getSsn().equalsIgnoreCase(newStudentRequest.getSsn())
                || student.getPhoneNumber().equalsIgnoreCase(newStudentRequest.getPhoneNumber())
                || student.getEmail().equalsIgnoreCase(newStudentRequest.getEmail())
                || student.getStudentNumber().equalsIgnoreCase(newStudentRequest.getStudentNumber());
    }

    public boolean existBySnn(String ssn) {
        return studentRepository.existsBySsn(ssn);
    }

    public List<StudentResponse> getAllStudentBy(String ssn) {
        return studentRepository.getStudentByAdvisorTeacher_Ssn(ssn).stream().map(responseObjectService::createStudentResponse)
                .collect(Collectors.toList());
    }
}
