package com.project.schoolmanagment.payload.Dto;

 import com.project.schoolmanagment.entity.concretes.Student;
import com.project.schoolmanagment.payload.request.StudentRequest;


public class StudentRequestDto {

    public Student dtoStudent(StudentRequest studentRequest) {
        return Student.builder().fatherName(studentRequest.getFatherName())
                .birthDay(studentRequest.getBirthDay())
                .name(studentRequest.getName())
                .surname(studentRequest.getSurname())
                .motherName(studentRequest.getMotherName())
                .password(studentRequest.getPassword())
                .username(studentRequest.getUsername())
                .ssn(studentRequest.getSsn())
                .birthPlace(studentRequest.getBirthPlace())
                .phoneNumber(studentRequest.getPhoneNumber())
                .email(studentRequest.getEmail())
                .gender(studentRequest.getGender())
                .build();
    }

}
