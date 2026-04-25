package com.project.schoolmanagment.repository;

import com.project.schoolmanagment.entity.concretes.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher,Long> {

	boolean existsByUsername(String username);

	boolean existsBySsn (String ssn);

	boolean existsByPhoneNumber (String phone);

	Teacher findByUsernameEquals (String username);

	boolean existsByEmail(String email);

	List<Teacher>getTeachersByNameContaining(String teacherName);

	Teacher getTeachersByUsername(String userName);

}
