package com.project.schoolmanagment.payload.Dto;

import com.project.schoolmanagment.entity.concretes.Dean;
import com.project.schoolmanagment.entity.concretes.ViceDean;
import com.project.schoolmanagment.payload.request.DeanRequest;
import com.project.schoolmanagment.payload.request.ViceDeanRequest;
import lombok.Data;

@Data
public class DeanDto {

    public Dean dtoDean(DeanRequest deanRequest) {
        return Dean.builder()
                .birthDay(deanRequest.getBirthDay())
                .name(deanRequest.getName())
                .surname(deanRequest.getSurname())
                .password(deanRequest.getPassword())
                .ssn(deanRequest.getSsn())
                .birthPlace(deanRequest.getBirthPlace())
                .phoneNumber(deanRequest.getPhoneNumber())
                .gender(deanRequest.getGender())
                .build();
    }
}
