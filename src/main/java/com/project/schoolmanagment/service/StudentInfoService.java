package com.project.schoolmanagment.service;

import com.project.schoolmanagment.Exception.ConflictException;
import com.project.schoolmanagment.Exception.ResourceNotFoundException;
import com.project.schoolmanagment.entity.concretes.*;
import com.project.schoolmanagment.entity.enums.Note;
import com.project.schoolmanagment.payload.request.StudentInfoRequestWithoutTeacherId;
import com.project.schoolmanagment.payload.request.UpdateRequest.UpdateStudentInfoRequest;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.payload.response.StudentInfoResponse;
import com.project.schoolmanagment.repository.StudentInfoRepository;
import com.project.schoolmanagment.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentInfoService {
    private final StudentInfoRepository studentInfoRepository;
    private final LessonService lessonService;
    private final EducationTermService educationTermService;
    private final StudentService studentService;
    private final TeacherService teacherService;

    private final CreateResponseObjectService createResponseObjectService;
    @Value("${midterm.exam.impact.percentage}")
    private Double midtermExamPercentage;
    @Value("${final.exam.impact.percentage}")
    private Double finalExamPercentage;

    @Value("${regular.impact.percentage}")
    private Double regularPercentage;

    @Value("${compulsory.impact.percentage}")
    private Double compulsoryPercentage;

    public ResponseMessage<StudentInfoResponse> save(String username, StudentInfoRequestWithoutTeacherId studentInfoRequest) {
        Student student = studentService.getStudentById(studentInfoRequest.getStudentId());
        Teacher teacher = teacherService.getTeacherByUsername(username);
        Lesson lesson = lessonService.getLessonById(studentInfoRequest.getLessonId());
        EducationTerm educationTerm = educationTermService.getById(studentInfoRequest.getEducationTermId());

        if (checkSameLesson(studentInfoRequest.getStudentId(), lesson.getLessonName()))
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_LESSON_NAME, lesson.getLessonName()));

        Double noteAverage = calculateExamAverage(studentInfoRequest.getMidtermExam(), studentInfoRequest.getFinalExam(), lesson.isCompulsory());
        Note note = checkLetterGrade(noteAverage);
        StudentInfo studentInfo = createDto(studentInfoRequest, note, noteAverage);
        studentInfo.setStudent(student);
        studentInfo.setEducationTerm(educationTerm);
        studentInfo.setTeacher(teacher);
        studentInfo.setLesson(lesson);
        StudentInfo savedStudentInfo = studentInfoRepository.save(studentInfo);
        return ResponseMessage.<StudentInfoResponse>builder()
                .object(createResponse(savedStudentInfo))
                .message("Student Info saved Successfully")
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    public ResponseMessage<StudentInfoResponse> update(UpdateStudentInfoRequest studentInfoRequest, Long studentInfoId) {

        System.out.println("studentInfoRequest.getLessonId()" + studentInfoRequest.getLessonId());
        Lesson lesson = lessonService.getLessonById(studentInfoRequest.getLessonId());
        StudentInfo getStudentInfo = getStudentInfoById(studentInfoId);
        EducationTerm educationTerm = educationTermService.getById(studentInfoRequest.getEducationTermId());

        Double noteAverage = calculateExamAverage(studentInfoRequest.getMidtermExam(), studentInfoRequest.getFinalExam(), lesson.isCompulsory());
        Note note = checkLetterGrade(noteAverage);
        StudentInfo studentInfo = createUpdatedStudent(
                studentInfoRequest,
                studentInfoId,
                lesson,
                educationTerm,
                note,
                noteAverage
        );

        studentInfo.setStudent(getStudentInfo.getStudent());
        studentInfo.setTeacher(getStudentInfo.getTeacher());
        StudentInfo updatedStudentInfo = studentInfoRepository.save(studentInfo);

        return ResponseMessage.<StudentInfoResponse>builder()
                .object(createResponse(updatedStudentInfo))
                .message("Student Info updated Successfully")
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    private StudentInfo getStudentInfoById(Long studentInfoId) {
        if (!studentInfoRepository.existsByIdEquals(studentInfoId))
            throw new ResourceNotFoundException(String.format(Messages.STUDENT_INFO_NOT_FOUND, studentInfoId));
        return studentInfoRepository.findByIdEquals(studentInfoId);

    }

    public ResponseMessage deleteStudentInfo(Long id) {

        if (!studentInfoRepository.existsByIdEquals(id))
            throw new ResourceNotFoundException(String.format(Messages.STUDENT_INFO_NOT_FOUND, id));

        studentInfoRepository.deleteById(id);
        return ResponseMessage.<StudentInfoResponse>builder()
                .message("Student Info deleted Successfully")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public Page<StudentInfoResponse> getAll(Pageable pageable) {
        return studentInfoRepository.getAll(pageable).map(this::createResponse);
    }

    public Page<StudentInfoResponse> getAllForTeacher(Pageable pageable, String username) {
        return studentInfoRepository.findByTeacherId_UsernameEquals(username, pageable).map(this::createResponse);
    }

    public Page<StudentInfoResponse> getAllStudentInfoByStudent(String username, Pageable pageable) {
        boolean student = studentService.existByUsername(username);
        if (!student) throw new ResourceNotFoundException(String.format(Messages.STUDENT_INFO_NOT_FOUND_BY_USERNAME, username));
        return studentInfoRepository.findByStudentId_UsernameEquals(username, pageable).map(this::createResponse);
    }

    public StudentInfoResponse getStudentInfoByid(Long id) {
        if (!studentInfoRepository.existsByIdEquals(id))
            throw new ResourceNotFoundException(String.format(Messages.STUDENT_INFO_NOT_FOUND, id));
        return createResponse(studentInfoRepository.findByIdEquals(id));
    }

    public List<StudentInfoResponse> getStudentInfoByStudentId(Long studentId) {

        if (!studentService.existById(studentId))
            throw new ResourceNotFoundException(String.format(Messages.STUDENT_NOT_FOUND, studentId));
        if (!studentInfoRepository.existsByStudent_IdEquals(studentId))
            throw new ResourceNotFoundException(String.format(Messages.STUDENT_INFO_NOT_FOUND_BY_STUDENT_ID, studentId));

        return studentInfoRepository.findByStudent_IdEquals(studentId)
                .stream()
                .map(this::createResponse)
                .collect(Collectors.toList());
    }

    public Page<StudentInfoResponse> search(int page, int size, String sort, String type) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }
        return studentInfoRepository.findAll(pageable).map(this::createResponse);
    }


    private StudentInfo createDto(StudentInfoRequestWithoutTeacherId studentInfoRequest, Note note, Double average) {
        return StudentInfo.builder().infoNote(studentInfoRequest.getInfoNote())
                .absentee(studentInfoRequest.getAbsentee())
                .midtermExam(studentInfoRequest.getMidtermExam())
                .finalExam(studentInfoRequest.getFinalExam())
                .examAverage(average)
                .letterGrade(note)
                .build();
    }

    private StudentInfoResponse createResponse(StudentInfo studentInfo) {
        return StudentInfoResponse.builder()
                .lessonName(studentInfo.getLesson().getLessonName())
                .creditScore(studentInfo.getLesson().getCreditScore())
                .isCompulsory(studentInfo.getLesson().isCompulsory())
                .educationTerm(studentInfo.getEducationTerm().getTerm())
                .id(studentInfo.getId())
                .absentee(studentInfo.getAbsentee())
                .midtermExam(studentInfo.getMidtermExam())
                .infoNote(studentInfo.getInfoNote())
                .finalExam(studentInfo.getFinalExam())
                .note(studentInfo.getLetterGrade())
                .average(studentInfo.getExamAverage())
                .studentResponse(createResponseObjectService.createStudentResponse(studentInfo.getStudent())).build();
    }

    private StudentInfo createUpdatedStudent(UpdateStudentInfoRequest studentInfoRequest,
                                             Long studentInfoRequestId,
                                             Lesson lesson,
                                             EducationTerm educationTerm,
                                             Note note,
                                             Double average) {
        return StudentInfo.builder()
                .id(studentInfoRequestId)
                .infoNote(studentInfoRequest.getInfoNote())
                .midtermExam(studentInfoRequest.getMidtermExam())
                .absentee(studentInfoRequest.getAbsentee())
                .finalExam(studentInfoRequest.getFinalExam())
                .lesson(lesson)
                .educationTerm(educationTerm)
                .examAverage(average)
                .letterGrade(note)
                .build();
    }

    private Double calculateExamAverage(Double midtermExam, Double finalExam, boolean isCompulsory) {
        double rate = regularPercentage;
        if (isCompulsory)
            rate = compulsoryPercentage;

        return ((midtermExam * midtermExamPercentage) + (finalExam * finalExamPercentage));
    }


    private Note checkLetterGrade(Double average) {
        if (average < 50.0) {
            return Note.FF;
        } else if (average >= 50.0 && average < 55.0) {
            return Note.DD;

        } else if (average >= 55.0 && average < 60.0) {
            return Note.DC;

        } else if (average >= 60.0 && average < 65.0) {
            return Note.CC;
        } else if (average >= 65.0 && average < 70.0) {
            return Note.CB;
        } else if (average >= 70.0 && average < 75.0) {
            return Note.BB;
        } else if (average >= 75.0 && average < 85.0) {
            return Note.BA;
        } else {
            return Note.AA;
        }
    }

    private boolean checkSameLesson(Long studentId, String lessonName) {
        return studentInfoRepository.getAllByStudentId_Id(studentId).stream().anyMatch((e) -> e.getLesson().getLessonName().equalsIgnoreCase(lessonName));
    }


}
