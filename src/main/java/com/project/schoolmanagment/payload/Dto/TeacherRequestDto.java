package com.project.schoolmanagment.payload.Dto;

import com.project.schoolmanagment.entity.concretes.Teacher;
import com.project.schoolmanagment.payload.request.TeacherRequest;
import lombok.*;

@Data
public class TeacherRequestDto {

    public Teacher dtoTeacher(TeacherRequest teacherRequest) {

        return Teacher.builder().name(teacherRequest.getName())
                .surname(teacherRequest.getSurname())
                .ssn(teacherRequest.getSsn())
                .username(teacherRequest.getUsername())
                .birthDay(teacherRequest.getBirthDay())
                .birthPlace(teacherRequest.getBirthPlace())
                .birthPlace(teacherRequest.getBirthPlace())
                .password(teacherRequest.getPassword())
                .phoneNumber(teacherRequest.getPhoneNumber())
                .email(teacherRequest.getEmail())
                .isAdvisor(teacherRequest.isAdvisorTeacher())
                .gender(teacherRequest.getGender())
                .build();
    }
}
