package com.project.schoolmanagment.contoller;

import com.project.schoolmanagment.entity.concretes.Student;
import com.project.schoolmanagment.payload.request.ChooseLessonProgramWithId;
import com.project.schoolmanagment.payload.request.StudentRequest;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.payload.response.StudentResponse;
import com.project.schoolmanagment.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

	private final StudentService studentService;

	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
	@PostMapping("/save")
	public ResponseMessage<StudentResponse>saveStudent(@RequestBody @Valid StudentRequest studentRequest){
		return studentService.saveStudent(studentRequest);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
	@GetMapping("/changeStatus")
	public ResponseMessage changeStatus (@RequestParam Long id, @RequestParam boolean status){
		return studentService.changeStatus(id,status);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
	@GetMapping("/getAll")
	public List<StudentResponse> getAllStudents(){
		return studentService.getAllStudents();
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
	@PutMapping("/update/{id}")
	public ResponseMessage<StudentResponse>updateStudent(@PathVariable Long id,
	                                                      @RequestBody @Valid StudentRequest studentRequest){
		return studentService.updateStudent(id,studentRequest);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
	@DeleteMapping("/delete/{id}")
	public ResponseMessage deleteStudentById(@PathVariable Long id){
		return studentService.deleteStudentById(id);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
	@GetMapping("/getStudentByName")
	public List<StudentResponse>getStudentsByName(@RequestParam(name = "name") String studentName){
		return studentService.findStudentsByName(studentName);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANTMANAGER')")
	@GetMapping("/search")
	public Page<StudentResponse> search(
			@RequestParam(value = "page") int page,
			@RequestParam(value = "size") int size,
			@RequestParam(value = "sort") String sort,
			@RequestParam(value = "type") String type
	){
		return studentService.search(page,size,sort,type);
	}

	//TODO instead of returning entity type, DTO type should be returned
	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
	@GetMapping ("/getStudentById")
	public Student getStudentById(@RequestParam(name = "id") Long id){
		return studentService.getStudentById(id);
	}

	//TODO URL of this API is not suitable!!!!
	@PreAuthorize("hasAnyAuthority('TEACHER','ADMIN')")
	@GetMapping("/getAllByAdvisorId")
	public List<StudentResponse>getAllByAdvisoryTeacherUsername(HttpServletRequest request){
		String userName = request.getHeader("username");
		return studentService.getAllByAdvisoryUsername(userName);
	}

	@PreAuthorize("hasAnyAuthority('STUDENT','ADMIN')")
	@PostMapping("/chooseLesson")
	public ResponseMessage<StudentResponse>chooseLesson(HttpServletRequest request,
											@RequestBody @Valid ChooseLessonProgramWithId chooseLessonProgramWithId){
		String userName = request.getHeader("username");
		return studentService.chooseLesson(userName,chooseLessonProgramWithId);
	}


}
