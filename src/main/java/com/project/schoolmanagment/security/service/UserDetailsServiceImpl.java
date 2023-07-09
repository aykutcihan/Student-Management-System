package com.project.schoolmanagment.security.service;

import com.project.schoolmanagment.entity.concretes.*;
import com.project.schoolmanagment.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final AdminRepository adminRepository;
	private final TeacherRepository teacherRepository;
	private final DeanRepository deanRepository;
	private final ViceDeanRepository viceDeanRepository;
	private final StudentRepository studentRepository;


	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Admin admin = adminRepository.findByUsernameEquals(username);
		Teacher teacher = teacherRepository.findByUsernameEquals(username);
		Dean dean = deanRepository.findByUsernameEquals(username);
		ViceDean viceDean = viceDeanRepository.findByUsernameEquals(username);
		Student student = studentRepository.findByUsernameEquals(username);
		if (student != null) {
			return new UserDetailsImpl(
					student.getId(),
					student.getUsername(),
					student.getName(),
					false,
					student.getPassword(),
					student.getUserRole().getRoleType().name());
		} else if (teacher != null) {
			return new UserDetailsImpl(
					teacher.getId(),
					teacher.getUsername(),
					teacher.getName(),
					false,
					teacher.getPassword(),
					teacher.getUserRole().getRoleType().name());
		} else if (admin != null) {
			return new UserDetailsImpl(
					admin.getId(),
					admin.getUsername(),
					admin.getName(),
					false,
					admin.getPassword(),
					admin.getUserRole().getRoleType().name());
		} else if (dean!=null) {
			return new UserDetailsImpl(
					dean.getId(),
					dean.getUsername(),
					dean.getName(),
					false,
					dean.getPassword(),
					dean.getUserRole().getRoleType().name());
		} else if (viceDean!=null) {
			return new UserDetailsImpl(
					viceDean.getId(),
					viceDean.getUsername(),
					viceDean.getName(),
					false,
					viceDean.getPassword(),
					viceDean.getUserRole().getRoleType().name());
		}
		throw new UsernameNotFoundException("User '" + username+ "  ' not found");
	}

//
//	//TODO please use inheritance and refactor this method.
//	@Override
//	@Transactional
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//		User student = studentRepository.findByUsernameEquals(username);
//		if (student != null) {
//			return mapUserToUserDetailsImpl(student);
//		}
//		User teacher = teacherRepository.findByUsernameEquals(username);
//		if (teacher != null) {
//			return mapUserToUserDetailsImpl(teacher);
//		}
//		User admin = adminRepository.findByUsernameEquals(username);
//		if (admin != null) {
//			return mapUserToUserDetailsImpl(admin);
//		}
//		User dean = deanRepository.findByUsernameEquals(username); //200ms
//		if (dean!=null) {
//			return mapUserToUserDetailsImpl(dean);
//		}
//
//		User viceDean = viceDeanRepository.findByUsernameEquals(username); //200ms
//		if (viceDean!=null) {
//			return mapUserToUserDetailsImpl(viceDean);
//		}
//		throw new UsernameNotFoundException("User '" + username+ "  ' not found");
//	}
//
//	private UserDetailsImpl mapUserToUserDetailsImpl(User user){
//		return new UserDetailsImpl(
//				user.getId(),
//				user.getUsername(),
//				user.getName(),
//				false,
//				user.getPassword(),
//				user.getUserRole().getRoleType().name());
//	}
}
