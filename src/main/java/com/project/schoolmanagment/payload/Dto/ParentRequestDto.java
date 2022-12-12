package com.project.schoolmanagment.payload.Dto;

import com.project.schoolmanagment.entity.concretes.Parent;
import com.project.schoolmanagment.payload.request.ParentRequest;
import lombok.Data;

@Data
public class ParentRequestDto {

    public Parent dtoParent(ParentRequest parentRequest){
        return Parent.builder().name(parentRequest.getName())
                .surname(parentRequest.getSurname())
                .birthDay(parentRequest.getBirthDay())
                .birthPlace(parentRequest.getBirthPlace())
                .password(parentRequest.getPassword())
                .phoneNumber(parentRequest.getPhoneNumber())
                .ssn(parentRequest.getSsn())
                .phoneNumber(parentRequest.getPhoneNumber())
                .build();
    }
}
