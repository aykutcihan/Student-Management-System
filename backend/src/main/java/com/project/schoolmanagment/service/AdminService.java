package com.project.schoolmanagment.service;

import com.project.schoolmanagment.entity.concretes.Admin;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.exception.ConflictException;
import com.project.schoolmanagment.payload.request.AdminRequest;
import com.project.schoolmanagment.payload.response.AdminResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.repository.*;
import com.project.schoolmanagment.utils.ServiceHelpers;
import com.project.schoolmanagment.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final AdminRepository adminRepository;

	private final UserRoleService userRoleService;

	private final ServiceHelpers serviceHelpers;


	public ResponseMessage save (AdminRequest adminRequest){

		serviceHelpers.checkDuplicate(adminRequest.getUsername(), adminRequest.getSsn(), adminRequest.getPhoneNumber());

		Admin admin = mapAdminRequestToAdmin(adminRequest);
		admin.setBuilt_in(false);

		//if username is also Admin we are setting built_in prop. to FALSE
		if(Objects.equals(adminRequest.getUsername(),"Admin")){
			admin.setBuilt_in(true);
		}

		admin.setUserRole(userRoleService.getUserRole(RoleType.ADMIN));

		//we will implement password encoder here

		Admin savedAdmin = adminRepository.save(admin);

		//In response message savedAdmin instance may not be sent back to front-end.
		return ResponseMessage.<AdminResponse>builder()
				.message("Admin Saved")
				.httpStatus(HttpStatus.CREATED)
				.object(mapAdminToAdminResponse(savedAdmin))
				.build();
	}

	public Page<Admin> getAllAdmins(Pageable pageable){
		return adminRepository.findAll(pageable);
	}

	public String deleteAdmin(Long id){
		//we should check the database if it really exists
		Optional<Admin>admin = adminRepository.findById(id);
		//TODO please divide the cases and throw meaningful response messages
		if(admin.isPresent() && admin.get().isBuilt_in()){
			throw new ConflictException(Messages.NOT_PERMITTED_METHOD_MESSAGE);
		}

		if (admin.isPresent()){
			adminRepository.deleteById(id);
			//TODO move this hard coded part to Messages class and call this property.
			return "Admin is deleted successfully";
		}

		return String.format(Messages.NOT_FOUND_USER_MESSAGE,id);

	}

	// TODO move this mappers to mappers package and create a Admin class
	// make the same implementation as we do on DeanDto and ViceDeanDto classes

	private AdminResponse mapAdminToAdminResponse(Admin admin){
		return AdminResponse.builder()
				.userId(admin.getId())
				.username(admin.getUsername())
				.name(admin.getName())
				.surname(admin.getSurname())
				.phoneNumber(admin.getPhoneNumber())
				.gender(admin.getGender())
				.birthDay(admin.getBirthDay())
				.birthPlace(admin.getBirthPlace())
				.ssn(admin.getSsn())
				.build();
	}

	private Admin mapAdminRequestToAdmin(AdminRequest adminRequest){
		return Admin.builder()
				.username(adminRequest.getUsername())
				.name(adminRequest.getName())
				.surname(adminRequest.getSurname())
				.password(adminRequest.getPassword())
				.ssn(adminRequest.getSsn())
				.birthDay(adminRequest.getBirthDay())
				.birthPlace(adminRequest.getBirthPlace())
				.phoneNumber(adminRequest.getPhoneNumber())
				.gender(adminRequest.getGender())
				.build();
	}






	public long countAllAdmins(){
		return adminRepository.count();
	}

}
