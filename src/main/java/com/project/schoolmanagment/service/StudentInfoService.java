package com.project.schoolmanagment.service;

import com.project.schoolmanagment.Exception.ConflictException;
import com.project.schoolmanagment.Exception.ResourceNotFoundException;
import com.project.schoolmanagment.entity.concretes.Lesson;
import com.project.schoolmanagment.entity.concretes.Student;
import com.project.schoolmanagment.entity.concretes.StudentInfo;
import com.project.schoolmanagment.entity.concretes.Teacher;
import com.project.schoolmanagment.entity.enums.Note;
import com.project.schoolmanagment.payload.request.StudentInfoRequest;
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
    @Value("${midterm.exam.impact.percentage}")
    private Double midtermExamPercantage;
    @Value("${final.exam.impact.percentage}")
    private Double finalExamPercantage;

    public ResponseMessage<StudentInfoResponse> save(String ssn, StudentInfoRequestWithoutTeacherId studentInfoRequest) {
        Optional<Student> student = studentService.getStudentById(studentInfoRequest.getStudentId());
        Optional<Teacher> teacher = teacherService.getTeacherBySsn(ssn);
        Optional<Lesson> lesson = lessonService.getLessonById(studentInfoRequest.getLessonId());

        if (!lesson.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_LESSON_MESSAGE, studentInfoRequest.getLessonId()));

        } else if (!student.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE, studentInfoRequest.getStudentId()));

        } else if (!teacher.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE, ssn));
        } else if (checkSameLesson(studentInfoRequest.getStudentId(), lesson.get().getLessonName())) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_LESSON_NAME, lesson.get().getLessonName()));
        }

        Double noteAverage = calculateExamAverage(studentInfoRequest.getMidtermExam(), studentInfoRequest.getFinalExam());
        Note note = checkLetterGrade(noteAverage);
        StudentInfo studentInfo = createDto(studentInfoRequest, note, noteAverage);
        studentInfo.setStudentId(student.get());
        studentInfo.setTeacherId(teacher.get());
        studentInfo.setLessonName(lesson.get().getLessonName());
        StudentInfo savedStudentInfo = studentInfoRepository.save(studentInfo);
        return ResponseMessage.<StudentInfoResponse>builder()
                .object(createResponse(savedStudentInfo))
                .message("Student Info saved Successfully")
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    public ResponseMessage<StudentInfoResponse> update(UpdateStudentInfoRequest studentInfoRequest, Long studentInfoId) {
        Optional<Lesson> lesson = lessonService.getLessonById(studentInfoRequest.getLessonId());
        Optional<StudentInfo> getStudentInfo = studentInfoRepository.findById(studentInfoId);
        if (!getStudentInfo.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.STUDENT_INFO_NOT_FOUND, studentInfoId));
        } else if (!lesson.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_LESSON_MESSAGE, studentInfoRequest.getLessonId()));
        }

        System.out.println(studentInfoRequest.getMidtermExam() + " " + studentInfoRequest.getFinalExam());
        Double noteAverage = calculateExamAverage(studentInfoRequest.getMidtermExam(), studentInfoRequest.getFinalExam());
        Note note = checkLetterGrade(noteAverage);
        StudentInfo studentInfo = createUpdatedStudent(studentInfoRequest,
                studentInfoId, lesson.get().getLessonName(), note, noteAverage);

        studentInfo.setStudentId(getStudentInfo.get().getStudentId());
        studentInfo.setTeacherId(getStudentInfo.get().getTeacherId());
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
            throw new ResourceNotFoundException(String.format(Messages.STUDENT_INFO_NOT_FOUND, id));
        }
        studentInfoRepository.deleteById(id);
        return ResponseMessage.<StudentInfoResponse>builder()
                .message("Student Info deleted Successfully")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public Page<StudentInfoResponse> getAll(Pageable pageable) {
        return studentInfoRepository.getAll(pageable);
    }

    public Page<StudentInfoResponse> getAllForTeacher(Pageable pageable,String ssn) {
        return studentInfoRepository.findByTeacherId_SsnEquals( ssn,pageable);
    }

    public Page<StudentInfoResponse> getAllStudentInfoByStudent(Pageable pageable, String ssn) {
        boolean student = studentService.existBySnn(ssn);
        if (!student) throw new ResourceNotFoundException(String.format(Messages.STUDENT_INFO_NOT_FOUND, ssn));
        return studentInfoRepository.findByStudentId_SsnEquals(pageable, ssn);
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
        return StudentInfoResponse.builder().lessonName(studentInfo.getLessonName())
                .id(studentInfo.getId())
                .absentee(studentInfo.getAbsentee())
                .midtermExam(studentInfo.getMidtermExam())
                .infoNote(studentInfo.getInfoNote())
                .finalExam(studentInfo.getFinalExam())
                .note(studentInfo.getLetterGrade())
                .average(studentInfo.getExamAverage())
                .studentResponse(createResponseObjectService.createStudentResponse(studentInfo.getStudentId())).build();
    }

    private StudentInfo createUpdatedStudent(UpdateStudentInfoRequest studentInfoRequest,
                                             Long studentInfoRequestId,
                                             String lessonName,
                                             Note note,
                                             Double average) {
        return StudentInfo.builder().id(studentInfoRequestId)
                .infoNote(studentInfoRequest.getInfoNote())
                .midtermExam(studentInfoRequest.getMidtermExam())
                .absentee(studentInfoRequest.getAbsentee())
                .finalExam(studentInfoRequest.getFinalExam())
                .lessonName(lessonName)
                .examAverage(average)
                .letterGrade(note)
                .build();
    }

    private Double calculateExamAverage(Double midtermExam, Double finalExam) {
        return (midtermExam * midtermExamPercantage) + (finalExam * finalExamPercantage);
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
        return studentInfoRepository.getAllByStudentId_Id(studentId).stream().anyMatch((e) -> e.getLessonName().equalsIgnoreCase(lessonName));
    }


}
