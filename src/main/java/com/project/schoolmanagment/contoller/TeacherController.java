package com.project.schoolmanagment.contoller;

import com.project.schoolmanagment.payload.request.ChooseLessonTeacherRequest;
import com.project.schoolmanagment.payload.request.TeacherRequest;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.payload.response.TeacherResponse;
import com.project.schoolmanagment.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("teachers")
@RequiredArgsConstructor
public class TeacherController {

	private final TeacherService teacherService;

	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
	@PostMapping("/save")
	public ResponseMessage<TeacherResponse> saveTeacher(@RequestBody @Valid TeacherRequest teacherRequest){
		return teacherService.saveTeacher(teacherRequest);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
	@GetMapping("/getAll")
	public List<TeacherResponse> getAllTeacher(){
		return teacherService.getAllTeacher();
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
	@GetMapping("/getTeacherByName")
	public List<TeacherResponse> getTeacherByName(@RequestParam (name = "name") String teacherName){
		return teacherService.getTeacherByName(teacherName);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
	@DeleteMapping("/delete/{id}")
	public ResponseMessage deleteTeacherById(@PathVariable Long id){
		return teacherService.deleteTeacherById(id);
	}
	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
	@GetMapping("/getSavedTeacherById/{id}")
	public ResponseMessage<TeacherResponse> findTeacherById(@PathVariable Long id){
		return teacherService.getTeacherById(id);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
	@GetMapping("/search")
	public Page<TeacherResponse> search(
			@RequestParam(value = "page")int page,
			@RequestParam(value = "size") int size,
			@RequestParam(value = "sort") String sort,
			@RequestParam(value = "type") String type){
		return teacherService.findTeacherByPage(page,size,sort,type);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
	@PutMapping("/update/{userId}")
	public ResponseMessage<TeacherResponse> updateTeacher (@RequestBody @Valid TeacherRequest teacherRequest,
														   @PathVariable Long userId){
		return teacherService.updateTeacher(teacherRequest,userId);

	}





	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
	@PostMapping("/chooseLesson")
	public ResponseMessage<TeacherResponse> chooseLesson(@RequestBody @Valid ChooseLessonTeacherRequest chooseLessonTeacherRequest){
		return teacherService.chooseLesson(chooseLessonTeacherRequest);
	}













}
