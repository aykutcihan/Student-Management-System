package com.project.schoolmanagment.payload.Dto;

import com.project.schoolmanagment.entity.concretes.Lesson;
import com.project.schoolmanagment.entity.concretes.LessonProgram;
import com.project.schoolmanagment.payload.request.LessonProgramRequest;
import lombok.Data;

import java.util.Set;

@Data
public class LessonProgramDto {

    public LessonProgram dtoLessonProgram(LessonProgramRequest lessonProgramRequest, Set<Lesson> lessons){
        return LessonProgram.builder().startTime(lessonProgramRequest.getStartTime())
                .stopTime(lessonProgramRequest.getStopTime())
                .date(lessonProgramRequest.getDate())
                .lesson(lessons)
                .build();
    }

}
