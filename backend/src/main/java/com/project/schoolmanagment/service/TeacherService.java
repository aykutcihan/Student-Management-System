package com.project.schoolmanagment.service;

import com.project.schoolmanagment.entity.concretes.LessonProgram;
import com.project.schoolmanagment.entity.concretes.Teacher;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.exception.BadRequestException;
import com.project.schoolmanagment.exception.ResourceNotFoundException;
import com.project.schoolmanagment.payload.mappers.TeacherDto;
import com.project.schoolmanagment.payload.request.ChooseLessonTeacherRequest;
import com.project.schoolmanagment.payload.request.TeacherRequest;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.payload.response.TeacherResponse;
import com.project.schoolmanagment.repository.TeacherRepository;
import com.project.schoolmanagment.utils.CheckParameterUpdateMethod;
import com.project.schoolmanagment.utils.CheckSameLessonProgram;
import com.project.schoolmanagment.utils.Messages;
import com.project.schoolmanagment.utils.ServiceHelpers;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherService {

	private final TeacherRepository teacherRepository;

	private final LessonProgramService lessonProgramService;

	private final ServiceHelpers serviceHelpers;

	private final TeacherDto teacherDto;

	private final UserRoleService userRoleService;

	private final PasswordEncoder passwordEncoder;

	private final AdvisoryTeacherService advisoryTeacherService;

	private final CheckSameLessonProgram checkSameLessonProgram;

	public ResponseMessage<TeacherResponse>saveTeacher(TeacherRequest teacherRequest){
		//need to get lessonPrograms
		Set<LessonProgram> lessonProgramSet = lessonProgramService.getLessonProgramById(teacherRequest.getLessonsIdList());

		serviceHelpers.checkDuplicate(teacherRequest.getUsername(),
										teacherRequest.getSsn(),
										teacherRequest.getPhoneNumber(),
										teacherRequest.getEmail());


		Teacher teacher = teacherDto.mapTeacherRequestToTeacher(teacherRequest);
		teacher.setUserRole(userRoleService.getUserRole(RoleType.TEACHER));
		teacher.setLessonsProgramList(lessonProgramSet);
		teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));

		Teacher savedTeacher = teacherRepository.save(teacher);
		if(teacherRequest.isAdvisorTeacher()){
			advisoryTeacherService.saveAdvisoryTeacher(teacher);
		}

		return ResponseMessage.<TeacherResponse>builder()
				.message("Teacher saved successfully")
				.httpStatus(HttpStatus.CREATED)
				.object(teacherDto.mapTeacherToTeacherResponse(savedTeacher))
				.build();
	}


	public List<TeacherResponse>getAllTeacher(){
		return teacherRepository.findAll()
				.stream()
				.map(teacherDto::mapTeacherToTeacherResponse)
				.collect(Collectors.toList());
	}

	public List<TeacherResponse>getTeacherByName(String teacherName){
		return teacherRepository.getTeachersByNameContaining(teacherName)
				.stream()
				.map(teacherDto::mapTeacherToTeacherResponse)
				.collect(Collectors.toList());
	}

	public ResponseMessage deleteTeacherById(Long id){
		isTeacherExist(id);
		teacherRepository.deleteById(id);

		return ResponseMessage.builder()
				.message("Teacher deleted successfully")
				.httpStatus(HttpStatus.OK)
				.build();
	}

	private Teacher isTeacherExist(Long id){
		return teacherRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE,id)));
	}

	public ResponseMessage<TeacherResponse>getTeacherById(Long id){

		return ResponseMessage.<TeacherResponse>builder()
				.object(teacherDto.mapTeacherToTeacherResponse(isTeacherExist(id)))
				.message("Teacher successfully found")
				.httpStatus(HttpStatus.OK)
				.build();
	}

	public Page<TeacherResponse> findTeacherByPage(int page,int size,String sort,String type){
		Pageable pageable = serviceHelpers.getPageableWithProperties(page, size, sort, type);
		return teacherRepository.findAll(pageable).map(teacherDto::mapTeacherToTeacherResponse);
	}

	public ResponseMessage<TeacherResponse>updateTeacher(TeacherRequest teacherRequest, Long userId){
		Teacher teacher = isTeacherExist(userId);
		Set<LessonProgram>lessonPrograms = lessonProgramService.getLessonProgramById(teacherRequest.getLessonsIdList());
		if(!CheckParameterUpdateMethod.checkUniquePropertiesForTeacher(teacher,teacherRequest)){
			serviceHelpers.checkDuplicate(teacherRequest.getUsername(),
					teacherRequest.getSsn(),
					teacherRequest.getPhoneNumber(),
					teacherRequest.getEmail());
		}
		Teacher updatedTeacher = teacherDto.mapTeacherRequestToUpdatedTeacher(teacherRequest,userId);
		//props. that does not exist in mappers
		updatedTeacher.setPassword(passwordEncoder.encode(teacherRequest.getPassword()));
		updatedTeacher.setLessonsProgramList(lessonPrograms);
		updatedTeacher.setUserRole(userRoleService.getUserRole(RoleType.TEACHER));

		Teacher savedTeacher = teacherRepository.save(updatedTeacher);
		advisoryTeacherService.updateAdvisoryTeacher(teacherRequest.isAdvisorTeacher(),savedTeacher);

		return ResponseMessage.<TeacherResponse>builder()
				.object(teacherDto.mapTeacherToTeacherResponse(savedTeacher))
				.message("Teacher successfully updated")
				.httpStatus(HttpStatus.OK)
				.build();
	}

	public ResponseMessage<TeacherResponse>chooseLesson (ChooseLessonTeacherRequest chooseLessonTeacherRequest){
		Teacher teacher = isTeacherExist(chooseLessonTeacherRequest.getTeacherId());

		Set<LessonProgram>lessonPrograms = lessonProgramService.getLessonProgramById(chooseLessonTeacherRequest.getLessonProgramId());

		Set<LessonProgram>teachersLessonProgram = teacher.getLessonsProgramList();

		checkSameLessonProgram.checkLessonPrgrams(teachersLessonProgram ,lessonPrograms);
		teachersLessonProgram.addAll(lessonPrograms);
		teacher.setLessonsProgramList(teachersLessonProgram);
		Teacher updatedTeacher = teacherRepository.save(teacher);

		return ResponseMessage.<TeacherResponse>builder()
				.message("Lesson Program added to teacher")
				.httpStatus(HttpStatus.CREATED)
				.object(teacherDto.mapTeacherToTeacherResponse(updatedTeacher))
				.build();

	}

	public Teacher getTeacherByUsername(String username){
		if(!teacherRepository.existsByUsername(username)){
			throw new ResourceNotFoundException(Messages.NOT_FOUND_USER_MESSAGE);
		}
		return teacherRepository.getTeachersByUsername(username);
	}










}
