package com.project.schoolmanagment.contoller;

import com.project.schoolmanagment.payload.request.EducationTermRequest;
import com.project.schoolmanagment.payload.response.EducationTermResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.service.EducationTermService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("educationTerms")
@RequiredArgsConstructor
public class EducationTermController {

	private  final EducationTermService educationTermService;

	@PostMapping("/save")
	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
	public ResponseMessage<EducationTermResponse>saveEducationTerm(@RequestBody @Valid EducationTermRequest educationTermRequest){
		return educationTermService.saveEducationTerm(educationTermRequest);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER')")
	@GetMapping("/{id}")
	public EducationTermResponse getEducationTermById(@PathVariable Long id){
		return educationTermService.getEducationTermResponseById(id);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER')")
	@GetMapping("getAll")
	public List<EducationTermResponse> getAllEducationTerms(){
		return educationTermService.getAllEducationTerms();
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER')")
	@GetMapping("/search")
	public Page<EducationTermResponse> getAllEducationTermsPyPage(
			@RequestParam (value = "page",defaultValue = "0") int page,
			@RequestParam(value = "size",defaultValue = "10") int size,
			@RequestParam(value = "sort", defaultValue = "startDate") String sort,
			@RequestParam(value = "type", defaultValue = "desc") String type){
		return educationTermService.getAllEducationTermsByPage(page,size,sort,type);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER')")
	@DeleteMapping("/delete/{id}")
	public ResponseMessage<?>deleteEducationTermById(@PathVariable Long id){
		return educationTermService.deleteEducationTermById(id);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
	@PutMapping("/update/{id}")
	public ResponseMessage<EducationTermResponse>updateEducationTerm(@PathVariable Long id,
	                                                                  @RequestBody @Valid EducationTermRequest educationTermRequest ){
		return educationTermService.updateEducationTerm(id,educationTermRequest);
	}

	//TODO homework  please write down a request that gets all education term starts dates later then entered Date
	//  hint - > should be get request with parameter






}
