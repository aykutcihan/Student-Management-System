package com.project.schoolmanagment.payload.mappers;

import com.project.schoolmanagment.entity.concretes.EducationTerm;
import com.project.schoolmanagment.payload.request.EducationTermRequest;
import com.project.schoolmanagment.payload.response.EducationTermResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class EducationTermDto {

	public EducationTerm mapEducationTermRequestToEducationTerm(EducationTermRequest educationTermRequest){
		return EducationTerm.builder()
				.term(educationTermRequest.getTerm())
				.startDate(educationTermRequest.getStartDate())
				.endDate(educationTermRequest.getEndDate())
				.lastRegistrationDate(educationTermRequest.getLastRegistrationDate())
				.build();
	}

	public EducationTerm mapEducationTermRequestToUpdatedEducationTerm(Long id,EducationTermRequest educationTermRequest){
		return mapEducationTermRequestToEducationTerm(educationTermRequest)
				.toBuilder()
				.id(id)
				.build();
	}

	public EducationTermResponse mapEducationTermToEducationTermResponse(EducationTerm educationTerm){
		return EducationTermResponse.builder()
				.id(educationTerm.getId())
				.term(educationTerm.getTerm())
				.startDate(educationTerm.getStartDate())
				.endDate(educationTerm.getEndDate())
				.lastRegistrationDate(educationTerm.getLastRegistrationDate())
				.build();
	}



}
