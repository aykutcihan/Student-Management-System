package com.project.schoolmanagment.service;

import com.project.schoolmanagment.entity.concretes.*;
import com.project.schoolmanagment.entity.enums.Note;
import com.project.schoolmanagment.exception.ConflictException;
import com.project.schoolmanagment.exception.ResourceNotFoundException;
import com.project.schoolmanagment.payload.mappers.StudentInfoDto;
import com.project.schoolmanagment.payload.request.StudentInfoRequest;
import com.project.schoolmanagment.payload.request.UpdateStudentInfoRequest;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.payload.response.StudentInfoResponse;
import com.project.schoolmanagment.repository.StudentInfoRepository;
import com.project.schoolmanagment.utils.Messages;
import com.project.schoolmanagment.utils.ServiceHelpers;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentInfoService {

	private final StudentService studentService;
	private final TeacherService teacherService;
	private final EducationTermService educationTermService;
	private final LessonService lessonService;
	private final StudentInfoRepository studentInfoRepository;
	private final StudentInfoDto studentInfoDto;
	private final ServiceHelpers serviceHelpers;
	@Value("${midterm.exam.impact.percentage}")
	private Double midtermExamPercentage;
	@Value("${final.exam.impact.percentage}")
	private Double finalExamPercentage;

	public ResponseMessage<StudentInfoResponse>saveStudentInfo(String teacherUsername, StudentInfoRequest studentInfoRequest){
		// we need entity for creation of StudentInfo
		Student student = studentService.isStudentsExist(studentInfoRequest.getStudentId());
		Teacher teacher = teacherService.getTeacherByUsername(teacherUsername);
		Lesson lesson = lessonService.isLessonExistById(studentInfoRequest.getLessonId());
		EducationTerm educationTerm = educationTermService.getEducationTermById(studentInfoRequest.getEducationTermId());

		//does student really have only one lesson accordind to this lesson name
		checkSameLesson(studentInfoRequest.getStudentId(), lesson.getLessonName());
		//we need grade calculation
		Note note = checkLetterGrade(calculateExamAverage(studentInfoRequest.getMidtermExam(),
															studentInfoRequest.getFinalExam()));

		//calling the mappers before persist to DB
		StudentInfo studentInfo = studentInfoDto.mapStudentInfoRequestToStudentInfo(
				studentInfoRequest,
				note,
				calculateExamAverage(studentInfoRequest.getMidtermExam(), studentInfoRequest.getFinalExam())
		);
		//set the values that does not exist in mappers
		studentInfo.setStudent(student);
		studentInfo.setEducationTerm(educationTerm);
		studentInfo.setTeacher(teacher);
		studentInfo.setLesson(lesson);
		//initialize another instance to return inside the response message
		StudentInfo savedStudentInfo = studentInfoRepository.save(studentInfo);

		return ResponseMessage.<StudentInfoResponse>builder()
				.message("Student Info Saved Successfully")
				.object(studentInfoDto.mapStudentInfoToStudentInfoResponse(savedStudentInfo))
				.httpStatus(HttpStatus.OK)
				.build();

	}

	public ResponseMessage<StudentInfoResponse>update(UpdateStudentInfoRequest studentInfoRequest,Long studentInfoId){
		Lesson lesson = lessonService.isLessonExistById(studentInfoRequest.getLessonId());
		StudentInfo studentInfo = isStudentInfoExistById(studentInfoId);
		EducationTerm educationTerm = educationTermService.getEducationTermById(studentInfoRequest.getEducationTermId());

		Double noteAverage = calculateExamAverage(studentInfoRequest.getMidtermExam(), studentInfoRequest.getFinalExam());
		Note note = checkLetterGrade(noteAverage);
		StudentInfo studentInfoForUpdate = studentInfoDto.mapStudentInfoUpdateToStudentInfo(studentInfoRequest,
																studentInfoId,
																lesson,
																educationTerm,
																note,
																noteAverage);
		//TODO move this setters to mapper
		studentInfoForUpdate.setStudent(studentInfo.getStudent());
		studentInfoForUpdate.setTeacher(studentInfo.getTeacher());
		StudentInfo updatedStudentInfo = studentInfoRepository.save(studentInfoForUpdate);
		return ResponseMessage.<StudentInfoResponse>builder()
				.message("Student info updated successfully")
				.httpStatus(HttpStatus.OK)
				.object(studentInfoDto.mapStudentInfoToStudentInfoResponse(updatedStudentInfo))
				.build();

	}


	private void checkSameLesson(Long studentId,String lessonName){
		boolean isLessonDupliucationExist = studentInfoRepository.getAllByStudentId_Id(studentId)
				.stream()
				.anyMatch((e)->e.getLesson().getLessonName().equalsIgnoreCase(lessonName));
		if(isLessonDupliucationExist){
			throw new ConflictException(String.format(Messages.ALREADY_REGISTER_LESSON_MESSAGE,lessonName));
		}
	}

	public Page<StudentInfoResponse>getAllForTeacher(HttpServletRequest httpServletRequest, int page, int size){
		Pageable pageable = serviceHelpers.getPageableWithProperties(page, size);
		String username = (String) httpServletRequest.getAttribute("username");
		return studentInfoRepository
				.findByTeacherId_UsernameEquals(username,pageable)
				.map(studentInfoDto::mapStudentInfoToStudentInfoResponse);
	}

	public Page<StudentInfoResponse>getAllForStudent(HttpServletRequest httpServletRequest, int page, int size){
		Pageable pageable = serviceHelpers.getPageableWithProperties(page, size);
		String username = (String) httpServletRequest.getAttribute("username");
		return studentInfoRepository
				.findByStudentId_UsernameEquals(username,pageable)
				.map(studentInfoDto::mapStudentInfoToStudentInfoResponse);
	}

	public List<StudentInfoResponse> getStudentInfoByStudentId(Long studentId){
		studentService.isStudentsExist(studentId);
		if(!studentInfoRepository.existsByStudent_IdEquals(studentId)){
			throw new ResourceNotFoundException(String.format(Messages.STUDENT_INFO_NOT_FOUND_BY_STUDENT_ID,studentId));
		}

		return studentInfoRepository.findByStudent_IdEquals(studentId)
				.stream()
				.map(studentInfoDto::mapStudentInfoToStudentInfoResponse)
				.collect(Collectors.toList());
	}

	public StudentInfoResponse findStudentInfoById(Long id){
		return studentInfoDto.mapStudentInfoToStudentInfoResponse(isStudentInfoExistById(id));
	}



	private Double calculateExamAverage(Double midtermExam, Double finalExam){
		return ((midtermExam* midtermExamPercentage) + (finalExam*finalExamPercentage));
	}

	private Note checkLetterGrade(Double average){

		if(average<50.0) {
			return Note.FF;
		} else if (average>=50.0 && average<55) {
			return Note.DD;
		} else if (average>=55.0 && average<60) {
			return Note.DC;
		} else if (average>=60.0 && average<65) {
			return Note.CC;
		} else if (average>=65.0 && average<70) {
			return Note.CB;
		} else if (average>=70.0 && average<75) {
			return Note.BB;
		} else if (average>=75.0 && average<80) {
			return Note.BA;
		} else {
			return Note.AA;
		}
	}


	public ResponseMessage deleteStudentInfo(Long studentInfoId){
		StudentInfo studentInfo = isStudentInfoExistById(studentInfoId);
		studentInfoRepository.deleteById(studentInfo.getId());

		return ResponseMessage.builder()
				.message("Student Info deleted Successfully")
				.httpStatus(HttpStatus.OK)
				.build();
	}

	public StudentInfo isStudentInfoExistById(Long id){
		boolean isExist = studentInfoRepository.existsByIdEquals(id);
		if(!isExist){
			throw new ResourceNotFoundException(String.format(Messages.STUDENT_INFO_NOT_FOUND,id));
		} else {
			return studentInfoRepository.findById(id).get();
		}
	}

	public Page<StudentInfoResponse> search(int page,int size, String sort,String type){
		Pageable pageable = serviceHelpers.getPageableWithProperties(page, size, sort, type);
		return studentInfoRepository.findAll(pageable).map(studentInfoDto::mapStudentInfoToStudentInfoResponse);
	}


}
