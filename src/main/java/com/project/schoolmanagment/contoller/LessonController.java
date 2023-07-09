package com.project.schoolmanagment.contoller;

import com.project.schoolmanagment.entity.concretes.Lesson;
import com.project.schoolmanagment.payload.request.LessonRequest;
import com.project.schoolmanagment.payload.response.LessonResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("lessons")
@RequiredArgsConstructor
public class LessonController {


	private final LessonService lessonService;

	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
	@PostMapping("/save")
	public ResponseMessage<LessonResponse> saveLesson(@RequestBody @Valid LessonRequest lessonRequest){
			return lessonService.saveLesson(lessonRequest);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
	@DeleteMapping("/delete/{id}")
	public ResponseMessage deleteLesson(@PathVariable Long id){
		return lessonService.deleteLessonById(id);
	}

	@GetMapping("/getLessonByName")
	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
	public ResponseMessage<LessonResponse> getLessonByLessonName(@RequestParam String lessonName){
		return lessonService.getLessonByLessonName(lessonName);
	}
	@GetMapping("/search")
	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
	public Page<LessonResponse> search(
			@RequestParam(value = "page") int page,
			@RequestParam(value = "size") int size,
			@RequestParam(value = "sort") String sort,
			@RequestParam(value = "type") String type){
		return lessonService.findLessonByPage(page,size,sort,type);
	}

	@GetMapping("/getAllLessonByLessonId")
	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
	public Set<Lesson> getAllLessonsByLessonId(@RequestParam(name = "lessonId") Set<Long> idSet){
		return lessonService.getLessonByLessonIdSet(idSet);
	}

	//TODO please make an update end-point


}
