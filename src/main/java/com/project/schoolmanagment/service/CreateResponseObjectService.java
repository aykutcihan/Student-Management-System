package com.project.schoolmanagment.service;

import com.project.schoolmanagment.entity.concretes.Student;
import com.project.schoolmanagment.entity.concretes.Teacher;
import com.project.schoolmanagment.payload.response.StudentResponse;
import com.project.schoolmanagment.payload.response.TeacherResponse;
import com.project.schoolmanagment.repository.StudentRepository;
import com.project.schoolmanagment.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateResponseObjectService {

    public TeacherResponse createTeacherResponse(Teacher teacher){
        return TeacherResponse.builder().teacherId(teacher.getId())
                .name(teacher.getName())
                .surname(teacher.getSurname())
                .ssn(teacher.getSsn())
                .phoneNumber(teacher.getPhoneNumber())
                .build();
    }

    public StudentResponse createStudentResponse(Student student) {
        return StudentResponse.builder().studentId(student.getId())
                .name(student.getName())
                .surname(student.getSurname())
                .ssn(student.getSsn())
                .phoneNumber(student.getPhoneNumber())
                .build();
    }
}
