package com.project.schoolmanagment.payload.Dto;

import com.project.schoolmanagment.entity.concretes.Lesson;
import com.project.schoolmanagment.entity.concretes.Student;
import com.project.schoolmanagment.payload.request.StudentRequest;
import lombok.Data;
import lombok.Getter;

import java.util.Set;

public class StudentRequestDto {

    public Student dtoStudent(StudentRequest studentRequest){
        return Student.builder().fatherName(studentRequest.getFatherName())
                .studentNumber(studentRequest.getStudentNumber())
                .birthDay(studentRequest.getBirthDay())
                .name(studentRequest.getName())
                .surname(studentRequest.getSurname())
                .motherName(studentRequest.getMotherName())
                .password(studentRequest.getPassword())
                .ssn(studentRequest.getSsn())
                .birthPlace(studentRequest.getBirthPlace())
                .build();
    }
}
