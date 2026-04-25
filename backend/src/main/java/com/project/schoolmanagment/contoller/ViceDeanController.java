package com.project.schoolmanagment.contoller;

import com.project.schoolmanagment.payload.request.ViceDeanRequest;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.payload.response.ViceDeanResponse;
import com.project.schoolmanagment.service.ViceDeanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("vicedean")
@RequiredArgsConstructor
public class ViceDeanController {


	private final ViceDeanService viceDeanService;

	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
	@PostMapping("/save")
	public ResponseMessage<ViceDeanResponse> saveViceDean(@RequestBody @Valid ViceDeanRequest viceDeanRequest){
		return viceDeanService.saveViceDean(viceDeanRequest);
	}
	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
	@PutMapping("/update/{userId}")
	public ResponseMessage<ViceDeanResponse> updateViceDean(@RequestBody @Valid ViceDeanRequest viceDeanRequest
															,@PathVariable Long userId){
		return viceDeanService.updateViceDean(viceDeanRequest,userId);

	}


	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
	@DeleteMapping("/delete/{userId}")
	public ResponseMessage<?> deleteViceDeanByAdmin(@PathVariable Long userId){
		return viceDeanService.deleteViceDeanByUserId(userId);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
	@GetMapping("getViceDeanById/{userId}")
	public ResponseMessage<ViceDeanResponse> findViceDeanByViceDeanId(@PathVariable Long userId){
		return viceDeanService.getViceDeanByViceDeanId(userId);
	}
	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
	@GetMapping("/getAll")
	public List<ViceDeanResponse> getAllViceDeans(){
		return viceDeanService.getAllViceDeans();
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
	@GetMapping("/search")
	public Page<ViceDeanResponse> getAllViceDeansByPage(
			@RequestParam(value = "page",defaultValue = "0",required = false) int page,
			@RequestParam(value = "size") int size,
			@RequestParam(value = "sort",defaultValue = "name") String sort,
			@RequestParam(value = "type",defaultValue = "desc") String type){

		return viceDeanService.getAllViceDeansByPage(page,size,sort,type);
	}





}
