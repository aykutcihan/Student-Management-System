package com.project.schoolmanagment.contoller;

import com.project.schoolmanagment.entity.concretes.Admin;
import com.project.schoolmanagment.payload.request.AdminRequest;
import com.project.schoolmanagment.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {

	private final AdminService adminService;

	@PostMapping("/save")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<?>save(@RequestBody @Valid AdminRequest adminRequest){
		return ResponseEntity.ok(adminService.save(adminRequest));
	}


	@GetMapping("/getAll")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<Page<Admin>>getAll(
			@RequestParam(value = "page",defaultValue = "0") int page,
			@RequestParam(value = "size",defaultValue = "10") int size,
			@RequestParam(value = "sort",defaultValue = "name") String sort,
			@RequestParam(value = "type",defaultValue = "desc") String type
	){
		//TODO move this calculation to service layer
		Pageable pageable = PageRequest.of(page,size, Sort.by(sort).ascending());

		if(Objects.equals(type,"desc")){
			pageable = PageRequest.of(page,size,Sort.by(sort).descending());
		}

		//TODO return type should be an DTO (AdminResponse) this should be done in SERVICE
		Page<Admin>admins = adminService.getAllAdmins(pageable);
		return new ResponseEntity<>(admins, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<String> delete(@PathVariable Long id){
		return ResponseEntity.ok(adminService.deleteAdmin(id));
	}






}
