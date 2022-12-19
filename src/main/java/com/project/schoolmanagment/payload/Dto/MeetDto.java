package com.project.schoolmanagment.payload.Dto;

import com.project.schoolmanagment.entity.concretes.Meet;
import com.project.schoolmanagment.payload.request.MeetRequest;
import lombok.Data;

@Data
public class MeetDto {

    public Meet meetDto(MeetRequest meetRequest) {
        return Meet.builder().startTime(meetRequest.getStartTime())
                .stopTime(meetRequest.getStopTime())
                .date(meetRequest.getDate())
                .description(meetRequest.getDescription()).build();
    }
}
