package com.project.schoolmanagment.service;

import com.project.schoolmanagment.Exception.BadRequestException;
import com.project.schoolmanagment.Exception.ResourceNotFoundException;
import com.project.schoolmanagment.entity.concretes.*;
import com.project.schoolmanagment.payload.request.StudentInfoRequest;
import com.project.schoolmanagment.payload.request.StudentRequest;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.payload.response.StudentInfoResponse;
import com.project.schoolmanagment.repository.StudentInfoRepository;
import com.project.schoolmanagment.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentInfoService {
    private final StudentInfoRepository studentInfoRepository;
    private final LessonService lessonService;
    private final StudentService studentService;
    private final TeacherService teacherService;

    private final CreateResponseObjectService createResponseObjectService;

    public ResponseMessage<StudentInfoResponse> save(StudentInfoRequest studentInfoRequest) {
        Optional<Student> student = studentService.getStudentById(studentInfoRequest.getStudentId());
        ResponseMessage<Teacher> teacher = teacherService.getTeacherById(studentInfoRequest.getTeacherId());
        Optional<Lesson> lesson = lessonService.getLessonById(studentInfoRequest.getLessonId());

        if (!lesson.isPresent()) {
            throw new BadRequestException(String.format(Messages.NOT_FOUND_LESSON_MESSAGE, studentInfoRequest.getLessonId()));

        } else if (!student.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE, studentInfoRequest.getStudentId()));

        } else if (teacher.getObject() == null) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE, studentInfoRequest.getTeacherId()));

        }
        StudentInfo studentInfo = createDto(studentInfoRequest);
        studentInfo.setStudentId(student.get());
        studentInfo.setTeacherId(teacher.getObject());
        studentInfo.setLessonName(lesson.get().getLessonName());
        StudentInfo savedStudentInfo = studentInfoRepository.save(studentInfo);
        return ResponseMessage.<StudentInfoResponse>builder()
                .object(createResponse(savedStudentInfo))
                .message("Student Info saved Successfully")
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    public ResponseMessage<StudentInfoResponse> update(StudentInfoRequest studentInfoRequest, Long studentInfoId) {
        Optional<Student> student = studentService.getStudentById(studentInfoRequest.getStudentId());
        ResponseMessage<Teacher> teacher = teacherService.getTeacherById(studentInfoRequest.getTeacherId());
        Optional<Lesson> lesson = lessonService.getLessonById(studentInfoRequest.getLessonId());

        if (!studentInfoRepository.findById(studentInfoId).isPresent()) {
            throw new BadRequestException(String.format(Messages.STUDENT_INFO_NOT_FOUND, studentInfoId));
        } else if (!lesson.isPresent()) {
            throw new BadRequestException(String.format(Messages.NOT_FOUND_LESSON_MESSAGE, studentInfoRequest.getLessonId()));

        } else if (!student.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE, studentInfoRequest.getStudentId()));

        } else if (teacher.getObject() == null) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE, studentInfoRequest.getTeacherId()));

        }

        StudentInfo studentInfo = createUpdatedStudent(studentInfoRequest, studentInfoId);
        studentInfo.setTeacherId(teacher.getObject());
        studentInfo.setStudentId(student.get());
        studentInfo.setLessonName(lesson.get().getLessonName());
        StudentInfo updatedStudentInfo = studentInfoRepository.save(studentInfo);

        return ResponseMessage.<StudentInfoResponse>builder()
                .object(createResponse(updatedStudentInfo))
                .message("Student Info updated Successfully")
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    public ResponseMessage deleteStudentInfo(Long id) {
        Optional<StudentInfo> studentInfo = studentInfoRepository.findById(id);
        if (!studentInfo.isPresent()) {
            throw new BadRequestException(String.format(Messages.STUDENT_INFO_NOT_FOUND, id));
        }
        studentInfoRepository.deleteById(id);
        return ResponseMessage.<StudentInfoResponse>builder()
                .message("Student Info deleted Successfully")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public List<StudentInfoResponse> getAll() {
        return studentInfoRepository.findAll().stream().map(this::createResponse).collect(Collectors.toList());
    }

    public List<StudentInfoResponse> getAllStudentInfoByStudent(Long studentId) {
        Optional<Student> student = studentService.getStudentById(studentId);
        if (!student.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE, studentId));
        }
        return studentInfoRepository.getAllByStudentId_Id(studentId).stream().map(this::createResponse).collect(Collectors.toList());
    }

    private StudentInfo createDto(StudentInfoRequest studentInfoRequest) {
        return StudentInfo.builder().infoNote(studentInfoRequest.getInfoNote())
                .absentee(studentInfoRequest.getAbsentee())
                .midtermExam(studentInfoRequest.getMidtermExam())
                .finalExam(studentInfoRequest.getFinalExam()).build();
    }

    private StudentInfoResponse createResponse(StudentInfo studentInfo) {
        return StudentInfoResponse.builder().lessonName(studentInfo.getLessonName())
                .id(studentInfo.getId())
                .absentee(studentInfo.getAbsentee())
                .midtermExam(studentInfo.getMidtermExam())
                .infoNote(studentInfo.getInfoNote())
                .finalExam(studentInfo.getFinalExam())
                .studentResponse(createResponseObjectService.createStudentResponse(studentInfo.getStudentId())).build();
    }

    private StudentInfo createUpdatedStudent(StudentInfoRequest studentInfoRequest, Long studentInfoRequestId) {
        return StudentInfo.builder().id(studentInfoRequestId)
                .infoNote(studentInfoRequest.getInfoNote())
                .midtermExam(studentInfoRequest.getMidtermExam())
                .absentee(studentInfoRequest.getAbsentee())
                .finalExam(studentInfoRequest.getFinalExam())
                .build();
    }


}
