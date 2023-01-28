package com.project.schoolmanagment.payload.Dto;

import com.project.schoolmanagment.entity.concretes.Dean;
 import com.project.schoolmanagment.payload.request.DeanRequest;
 import lombok.Data;

@Data
public class DeanDto {

    public Dean dtoDean(DeanRequest deanRequest) {
        return Dean.builder()
                .username(deanRequest.getUsername())
                .name(deanRequest.getName())
                .surname(deanRequest.getSurname())
                .password(deanRequest.getPassword())
                .ssn(deanRequest.getSsn())
                .birthDay(deanRequest.getBirthDay())
                .birthPlace(deanRequest.getBirthPlace())
                .phoneNumber(deanRequest.getPhoneNumber())
                .gender(deanRequest.getGender())
                .build();
    }
}
