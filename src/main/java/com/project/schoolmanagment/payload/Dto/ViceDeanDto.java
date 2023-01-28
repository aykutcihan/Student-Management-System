package com.project.schoolmanagment.payload.Dto;

import com.project.schoolmanagment.entity.concretes.ViceDean;
import com.project.schoolmanagment.payload.request.ViceDeanRequest;
import lombok.Data;

@Data
public class ViceDeanDto {

    public ViceDean dtoViceDean(ViceDeanRequest viceDeanRequest) {
        return ViceDean.builder()
                .birthDay(viceDeanRequest.getBirthDay())
                .username(viceDeanRequest.getUsername())
                .name(viceDeanRequest.getName())
                .surname(viceDeanRequest.getSurname())
                .password(viceDeanRequest.getPassword())
                .ssn(viceDeanRequest.getSsn())
                .birthPlace(viceDeanRequest.getBirthPlace())
                .phoneNumber(viceDeanRequest.getPhoneNumber())
                .gender(viceDeanRequest.getGender())
                .build();
    }
}
