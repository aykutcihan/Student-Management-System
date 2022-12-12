package com.project.schoolmanagment.service;

import com.project.schoolmanagment.entity.concretes.Lesson;
import com.project.schoolmanagment.entity.concretes.LessonProgram;
import com.project.schoolmanagment.entity.concretes.Parent;
import com.project.schoolmanagment.payload.Dto.LessonProgramDto;
import com.project.schoolmanagment.payload.request.LessonProgramRequest;
import com.project.schoolmanagment.payload.response.LessonProgramResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.repository.LessonProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonProgramService {

    private final LessonProgramRepository lessonProgramRepository;
    private final LessonProgramDto lessonProgramDto;

    private final LessonService lessonService;

    public ResponseMessage<LessonProgram> save(LessonProgramRequest lessonProgramRequest) {
        Set<Lesson> lessons = lessonService.getLessonByLessonNameList(lessonProgramRequest.getLessonIdList());
        if(lessons.size()==0){
            return ResponseMessage.<LessonProgram>builder()
                    .message("Not found Lesson")
                    .httpStatus(HttpStatus.NOT_FOUND).build();
        }
        LessonProgram lessonProgram = lessonProgramRequestToDto(lessonProgramRequest,lessons);
        LessonProgram savedLessonProgram = lessonProgramRepository.save(lessonProgram);
        return ResponseMessage.<LessonProgram>builder()
                .message("Created Lesson Program")
                .object(savedLessonProgram)
                .httpStatus(HttpStatus.CREATED).build();
    }

    /*
    public ResponseMessage<LessonProgram> save(LessonProgram lessonProgram) {
        //Set<Lesson> lessons = lessonService.getLessonByLessonNameList(lessonProgram.getLesson().);
        //LessonProgram lessonProgram = lessonProgramRequestToDto(lessonProgramRequest,lessons);
        LessonProgram savedLessonProgram = lessonProgramRepository.save(lessonProgram);
        return ResponseMessage.<LessonProgram>builder()
                .message("Created Lesson Program")
                .object(savedLessonProgram)
                .httpStatus(HttpStatus.CREATED).build();
    }

     */
    private LessonProgram lessonProgramRequestToDto(LessonProgramRequest lessonProgramRequest,Set<Lesson> lessons) {
        return lessonProgramDto.dtoLessonProgram(lessonProgramRequest,lessons);
    }

    public Set<LessonProgram> getLessonProgramById(Set<Long> lessonProgramIdList){
        return lessonProgramRepository.getLessonProgramByLessonProgramIdList(lessonProgramIdList);
    }

    public Set<LessonProgramResponse> createLessonProgramResponse(Set<LessonProgram> lessonProgram){
        return lessonProgram.stream().map((l)-> LessonProgramResponse.builder()
                .date(l.getDate())
                .startTime(l.getStartTime())
                .stopTime(l.getStopTime())
                .lessonProgramId(l.getId())
                .lessonName(l.getLesson())
                .build()).collect(Collectors.toSet());
    }
}
