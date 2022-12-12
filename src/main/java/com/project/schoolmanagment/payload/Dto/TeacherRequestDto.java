package com.project.schoolmanagment.payload.Dto;

import com.project.schoolmanagment.entity.concretes.Lesson;
import com.project.schoolmanagment.entity.concretes.LessonProgram;
import com.project.schoolmanagment.entity.concretes.Role;
import com.project.schoolmanagment.entity.concretes.Teacher;
import com.project.schoolmanagment.payload.request.TeacherRequest;
import lombok.*;

import java.util.Set;

@Data
public class TeacherRequestDto {

    public Teacher dtoTeacher(TeacherRequest teacherRequest, Set<LessonProgram> lessonSet) {

        return Teacher.builder().name(teacherRequest.getName())
                .surname(teacherRequest.getSurname())
                .ssn(teacherRequest.getSsn())
                .birthDay(teacherRequest.getBirthDay())
                .birthPlace(teacherRequest.getBirthPlace())
                .birthPlace(teacherRequest.getBirthPlace())
                .password(teacherRequest.getPassword())
                .phoneNumber(teacherRequest.getPhoneNumber())
                .lessons(lessonSet).build();
    }
}
