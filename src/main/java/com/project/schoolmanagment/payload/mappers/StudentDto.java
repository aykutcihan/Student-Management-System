package com.project.schoolmanagment.payload.mappers;

import com.project.schoolmanagment.entity.concretes.Student;
import com.project.schoolmanagment.payload.request.StudentRequest;
import com.project.schoolmanagment.payload.response.StudentResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class StudentDto {

	public Student mapStudentRequestToStudent(StudentRequest studentRequest){
		return Student.builder()
				.fatherName(studentRequest.getFatherName())
				.motherName(studentRequest.getMotherName())
				.birthDay(studentRequest.getBirthDay())
				.birthPlace(studentRequest.getBirthPlace())
				.name(studentRequest.getName())
				.surname(studentRequest.getSurname())
				.password(studentRequest.getPassword())
				.username(studentRequest.getUsername())
				.ssn(studentRequest.getSsn())
				.email(studentRequest.getEmail())
				.phoneNumber(studentRequest.getPhoneNumber())
				.gender(studentRequest.getGender())
				.build();
	}

	public Student mapStudentRequestToUpdatedStudent(StudentRequest studentRequest, Long studentId){
		Student student = mapStudentRequestToStudent(studentRequest);
		student.setId(studentId);
		return student;
	}

	public StudentResponse mapStudentToStudentResponse(Student student){
		return StudentResponse.builder()
				.userId(student.getId())
				.username(student.getUsername())
				.name(student.getName())
				.surname(student.getSurname())
				.birthDay(student.getBirthDay())
				.birthPlace(student.getBirthPlace())
				.phoneNumber(student.getPhoneNumber())
				.gender(student.getGender())
				.email(student.getEmail())
				.fatherName(student.getFatherName())
				.motherName(student.getMotherName())
				.studentNumber(student.getStudentNumber())
				.isActive(student.isActive())
				.build();
	}
}
