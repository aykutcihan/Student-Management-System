package com.project.schoolmanagment.service;

import com.project.schoolmanagment.entity.concretes.Lesson;
import com.project.schoolmanagment.entity.concretes.Student;
import com.project.schoolmanagment.entity.concretes.Teacher;
import com.project.schoolmanagment.payload.response.LessonResponse;
import com.project.schoolmanagment.payload.response.StudentResponse;
import com.project.schoolmanagment.payload.response.TeacherResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateResponseObjectService {

    public TeacherResponse createTeacherResponse(Teacher teacher) {
        return TeacherResponse.builder().userId(teacher.getId())
                .username(teacher.getUsername())
                .name(teacher.getName())
                .surname(teacher.getSurname())
                .birthDay(teacher.getBirthDay())
                .ssn(teacher.getSsn())
                .birthPlace(teacher.getBirthPlace())
                .phoneNumber(teacher.getPhoneNumber())
                .gender(teacher.getGender())
                .email(teacher.getEmail())
                .build();
    }

    public StudentResponse createStudentResponse(Student student) {
        return StudentResponse.builder().userId(student.getId())
                .username(student.getUsername())
                .name(student.getName())
                .surname(student.getSurname())
                .birthDay(student.getBirthDay())
                .birthPlace(student.getBirthPlace())
                .phoneNumber(student.getPhoneNumber())
                .gender(student.getGender())
                .email(student.getEmail())
                .motherName(student.getMotherName())
                .fatherName(student.getFatherName())
                .studentNumber(student.getStudentNumber())
                .isActive(student.isActive())
                .build();
    }

    public LessonResponse createLessonResponse(Lesson lesson) {
        return LessonResponse.builder()
                .lessonName(lesson.getLessonName())
                .isCompulsory(lesson.isCompulsory())
                .creditScore(lesson.getCreditScore())
                .build();
    }
}
