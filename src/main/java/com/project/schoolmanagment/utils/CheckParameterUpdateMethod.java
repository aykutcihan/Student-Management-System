package com.project.schoolmanagment.utils;

import com.project.schoolmanagment.entity.abstracts.User;
import com.project.schoolmanagment.entity.concretes.Student;
import com.project.schoolmanagment.entity.concretes.Teacher;
import com.project.schoolmanagment.payload.request.StudentRequest;
import com.project.schoolmanagment.payload.request.TeacherRequest;
import com.project.schoolmanagment.payload.request.abstracts.BaseUserRequest;

public class CheckParameterUpdateMethod {

	/**
	 *
	 * @param user a kind of entity that will be validated
	 * @param baseUserRequest DTO from UI to be changed
	 * @return true if they are the same
	 */
	public static boolean checkUniqueProperties(User user, BaseUserRequest baseUserRequest){
		return user.getSsn().equalsIgnoreCase(baseUserRequest.getSsn())
				|| user.getPhoneNumber().equalsIgnoreCase(baseUserRequest.getPhoneNumber())
				|| user.getUsername().equalsIgnoreCase(baseUserRequest.getUsername());
	}

	public static boolean checkUniquePropertiesForTeacher(Teacher teacher, TeacherRequest teacherRequest){
		return checkUniqueProperties(teacher,teacherRequest)
				|| teacher.getEmail().equalsIgnoreCase(teacherRequest.getEmail());
	}

	public static boolean checkUniquePropertiesForStudent(Student student, StudentRequest studentRequest){
		return checkUniqueProperties(student,studentRequest)
				|| student.getEmail().equalsIgnoreCase(studentRequest.getEmail());
	}



}
