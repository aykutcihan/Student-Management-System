package com.project.schoolmanagment.payload.Dto;

import com.project.schoolmanagment.entity.concretes.AssistantManager;
import com.project.schoolmanagment.entity.concretes.Student;
import com.project.schoolmanagment.payload.request.AssistantManagerRequest;
import com.project.schoolmanagment.payload.request.StudentRequest;
import lombok.Data;

@Data
public class AssistantManagerDto {

    public AssistantManager dtoAssistantManager(AssistantManagerRequest assistantManagerRequest){
        return AssistantManager.builder()
                .birthDay(assistantManagerRequest.getBirthDay())
                .name(assistantManagerRequest.getName())
                .surname(assistantManagerRequest.getSurname())
                .password(assistantManagerRequest.getPassword())
                .ssn(assistantManagerRequest.getSsn())
                .birthPlace(assistantManagerRequest.getBirthPlace())
                .phoneNumber(assistantManagerRequest.getPhoneNumber())
                .build();
    }
}
