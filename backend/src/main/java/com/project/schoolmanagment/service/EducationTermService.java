package com.project.schoolmanagment.service;

import com.project.schoolmanagment.entity.concretes.EducationTerm;
import com.project.schoolmanagment.exception.ResourceNotFoundException;
import com.project.schoolmanagment.payload.mappers.EducationTermDto;
import com.project.schoolmanagment.payload.request.EducationTermRequest;
import com.project.schoolmanagment.payload.response.EducationTermResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.repository.EducationTermRepository;
import com.project.schoolmanagment.utils.Messages;
import com.project.schoolmanagment.utils.ServiceHelpers;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EducationTermService {

	private final EducationTermRepository educationTermRepository;

	private final EducationTermDto educationTermDto;

	private final ServiceHelpers serviceHelpers;


	public ResponseMessage<EducationTermResponse> saveEducationTerm(EducationTermRequest educationTermRequest){

		validateEducationTermDates(educationTermRequest);

		EducationTerm savedEducationTerm = educationTermRepository.save(educationTermDto.mapEducationTermRequestToEducationTerm(educationTermRequest));

		return ResponseMessage.<EducationTermResponse>builder()
													.message("Education Term Saved")
													.object(educationTermDto.mapEducationTermToEducationTermResponse(savedEducationTerm))
													.httpStatus(HttpStatus.CREATED)
													.build();

	}

	public List<EducationTermResponse> getAllEducationTerms(){
		return educationTermRepository.findAll()
										.stream()
										.map(educationTermDto::mapEducationTermToEducationTermResponse)
										.collect(Collectors.toList());
	}


	public EducationTermResponse getEducationTermResponseById(Long id){

//		return educationTermDto.mapEducationTermToEducationTermResponse
//				(educationTermRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Could not find Education term with id " + id)));

		EducationTerm term = isEducationTermExist(id);

		return educationTermDto.mapEducationTermToEducationTermResponse(term);

	}

	public EducationTerm getEducationTermById(Long id){
		return isEducationTermExist(id);
	}


	public ResponseMessage<?>deleteEducationTermById(Long id){

		isEducationTermExist(id);

		educationTermRepository.deleteById(id);

		return ResponseMessage.builder()
				.message("Education Term Deleted Successfully")
				.httpStatus(HttpStatus.OK)
				.build();
	}


	public Page<EducationTermResponse> getAllEducationTermsByPage (int page,int size,String sort,String type){
		Pageable pageable = serviceHelpers.getPageableWithProperties(page, size, sort, type);

		return educationTermRepository.findAll(pageable).map(educationTermDto::mapEducationTermToEducationTermResponse);
	}

	public ResponseMessage<EducationTermResponse>updateEducationTerm(Long id,EducationTermRequest educationTermRequest){

		isEducationTermExist(id);

		validateEducationTermDatesForUpdate(educationTermRequest);

		EducationTerm educationTermUpdated = educationTermRepository.save(educationTermDto.mapEducationTermRequestToUpdatedEducationTerm(id,educationTermRequest));

		return ResponseMessage.<EducationTermResponse>builder()
				.message("Education Term Updated")
				.httpStatus(HttpStatus.OK)
				.object(educationTermDto.mapEducationTermToEducationTermResponse(educationTermUpdated))
				.build();

	}

	private EducationTerm isEducationTermExist(Long id){
		EducationTerm term = educationTermRepository.findByIdEquals(id);
		if(term==null){
			throw new ResourceNotFoundException(String.format(Messages.EDUCATION_TERM_NOT_FOUND_MESSAGE,id));
		} else {
			return term;
		}
	}



	private void validateEducationTermDates(EducationTermRequest educationTermRequest){

		//TODO another requirement can be needed for validating ->  registration > end
		// check the dates also for TODAY


		validateEducationTermDatesForUpdate(educationTermRequest);
		// we need one more validatetion in addition to validation above

		// if any education term exist in these year with this term
		if(educationTermRepository.existsByTermAndYear(educationTermRequest.getTerm(),educationTermRequest.getStartDate().getYear())){
			throw new ResourceNotFoundException(Messages.EDUCATION_TERM_IS_ALREADY_EXIST_BY_TERM_AND_YEAR_MESSAGE);
		}


	}

	private void validateEducationTermDatesForUpdate(EducationTermRequest educationTermRequest){

		//TODO another requirement can be needed for validating ->  registration > end
		// check the dates also for TODAY

		// registration > start
		if(educationTermRequest.getLastRegistrationDate().isAfter(educationTermRequest.getStartDate())){
			throw new ResourceNotFoundException(Messages.EDUCATION_START_DATE_IS_EARLIER_THAN_LAST_REGISTRATION_DATE);
		}

		// end > start
		if(educationTermRequest.getEndDate().isBefore(educationTermRequest.getStartDate())){
			throw new ResourceNotFoundException(Messages.EDUCATION_END_DATE_IS_EARLIER_THAN_START_DATE);
		}


	}








}
