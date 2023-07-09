package com.project.schoolmanagment.service;

import com.project.schoolmanagment.entity.concretes.ViceDean;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.exception.ResourceNotFoundException;
import com.project.schoolmanagment.payload.mappers.ViceDeanDto;
import com.project.schoolmanagment.payload.request.ViceDeanRequest;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.payload.response.ViceDeanResponse;
import com.project.schoolmanagment.repository.ViceDeanRepository;
import com.project.schoolmanagment.utils.CheckParameterUpdateMethod;
import com.project.schoolmanagment.utils.ServiceHelpers;
import com.project.schoolmanagment.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ViceDeanService {

	private final ViceDeanRepository viceDeanRepository;

	public final ServiceHelpers serviceHelpers;

	public final ViceDeanDto viceDeanDto;

	public final PasswordEncoder passwordEncoder;

	public final UserRoleService userRoleService;


	public ResponseMessage<ViceDeanResponse> saveViceDean(ViceDeanRequest viceDeanRequest) {

		serviceHelpers.checkDuplicate(viceDeanRequest.getUsername(),
				viceDeanRequest.getSsn(),
				viceDeanRequest.getPhoneNumber());

		ViceDean viceDean = viceDeanDto.mapViceDeanRequestToViceDean(viceDeanRequest);
		viceDean.setPassword(passwordEncoder.encode(viceDeanRequest.getPassword()));
		viceDean.setUserRole(userRoleService.getUserRole(RoleType.ASSISTANT_MANAGER));

		ViceDean savedViceDean = viceDeanRepository.save(viceDean);

		return ResponseMessage.<ViceDeanResponse>builder()
				.message("Vice Dean Saved")
				.httpStatus(HttpStatus.CREATED)
				.object(viceDeanDto.mapViceDeanToViceDeanResponse(savedViceDean))
				.build();
	}

	private Optional<ViceDean> isViceDeanExist(Long viceDeanId) {
		Optional<ViceDean> viceDean = viceDeanRepository.findById(viceDeanId);
		if (!viceDeanRepository.findById(viceDeanId).isPresent()) {
			throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE, viceDeanId));
		} else {
			return viceDean;
		}
	}

	public ResponseMessage<?> deleteViceDeanByUserId(Long viceDeanId) {

		viceDeanRepository.deleteById(viceDeanId);

		return ResponseMessage.builder()
				.message("Vice Dean deleted")
				.httpStatus(HttpStatus.OK)
				.build();
	}


	public ResponseMessage<ViceDeanResponse>getViceDeanByViceDeanId(Long viceDeanId){
		return ResponseMessage.<ViceDeanResponse>builder()
				.message("Vice Dean Found")
				.httpStatus(HttpStatus.OK)
				.object(viceDeanDto.mapViceDeanToViceDeanResponse(isViceDeanExist(viceDeanId).get()))
				.build();
	}

	public ResponseMessage<ViceDeanResponse> updateViceDean(ViceDeanRequest viceDeanRequest, Long viceDeanId){
		Optional<ViceDean>viceDean = isViceDeanExist(viceDeanId);

		if(!CheckParameterUpdateMethod.checkUniqueProperties(viceDean.get(),viceDeanRequest)){
			serviceHelpers.checkDuplicate(viceDeanRequest.getUsername(),
										viceDeanRequest.getSsn(),
										viceDeanRequest.getPhoneNumber());
		}

		ViceDean updatedViceDean = viceDeanDto.mapViceDeanRequestToUpdatedViceDean(viceDeanRequest,viceDeanId);
		updatedViceDean.setPassword(passwordEncoder.encode(viceDeanRequest.getPassword()));
		ViceDean savedViceDean = viceDeanRepository.save(updatedViceDean);

		return ResponseMessage.<ViceDeanResponse>builder()
				.message("Vice Dean Updated")
				.httpStatus(HttpStatus.OK)
				.object(viceDeanDto.mapViceDeanToViceDeanResponse(savedViceDean))
				.build();
	}

	//TODO learn more about stream API
	public List<ViceDeanResponse> getAllViceDeans(){
		return viceDeanRepository.findAll()
				.stream()
				.map(viceDeanDto::mapViceDeanToViceDeanResponse)
				.collect(Collectors.toList());
	}

	public Page<ViceDeanResponse> getAllViceDeansByPage(int page, int size, String sort, String type){

		//we created a helper method in utils package

//		Pageable pageable = PageRequest.of(page,size, Sort.by(sort).ascending());
//		if(Objects.equals(type,"desc")){
//			pageable = PageRequest.of(page,size, Sort.by(sort).descending());
//		}

		Pageable pageable = serviceHelpers.getPageableWithProperties(page, size, sort, type);

		return viceDeanRepository.findAll(pageable).map(viceDeanDto::mapViceDeanToViceDeanResponse);
	}



}
