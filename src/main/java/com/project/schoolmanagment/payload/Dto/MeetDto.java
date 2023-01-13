package com.project.schoolmanagment.payload.Dto;

import com.project.schoolmanagment.entity.concretes.Meet;
import com.project.schoolmanagment.payload.request.MeetRequest;
import com.project.schoolmanagment.payload.request.MeetRequestWithoutId;
import lombok.Data;

@Data
public class MeetDto {

    public Meet meetDto(MeetRequestWithoutId meetRequestWithoutId) {
        return Meet.builder().startTime(meetRequestWithoutId.getStartTime())
                .stopTime(meetRequestWithoutId.getStopTime())
                .date(meetRequestWithoutId.getDate())
                .description(meetRequestWithoutId.getDescription()).build();
    }
}
