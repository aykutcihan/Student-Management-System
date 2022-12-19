package com.project.schoolmanagment.service;

import com.project.schoolmanagment.Exception.BadRequestException;
import com.project.schoolmanagment.entity.concretes.Lesson;
import com.project.schoolmanagment.entity.concretes.LessonProgram;
import com.project.schoolmanagment.payload.Dto.LessonProgramDto;
import com.project.schoolmanagment.payload.request.LessonProgramRequest;
import com.project.schoolmanagment.payload.response.LessonProgramResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.repository.LessonProgramRepository;
import com.project.schoolmanagment.utils.Messages;
import com.project.schoolmanagment.utils.TimeControl;
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
    private final CreateResponseObjectService createResponseObjectService;

    public ResponseMessage<LessonProgram> save(LessonProgramRequest lessonProgramRequest) {
        Set<Lesson> lessons = lessonService.getLessonByLessonNameList(lessonProgramRequest.getLessonIdList());
        if(lessons.size()==0){
            return ResponseMessage.<LessonProgram>builder()
                    .message("Not found Lesson")
                    .httpStatus(HttpStatus.NOT_FOUND).build();
        }
        else if(TimeControl.check(lessonProgramRequest.getStartTime(),lessonProgramRequest.getStopTime()) ){
            throw new BadRequestException(Messages.TIME_NOT_VALID_MESSAGE);
        }
        LessonProgram lessonProgram = lessonProgramRequestToDto(lessonProgramRequest,lessons);
        LessonProgram savedLessonProgram = lessonProgramRepository.save(lessonProgram);
        return ResponseMessage.<LessonProgram>builder()
                .message("Created Lesson Program")
                .object(savedLessonProgram)
                .httpStatus(HttpStatus.CREATED).build();
    }

    public List<LessonProgram> getAllLessonProgram(){
        return lessonProgramRepository.findAll();
    }

    public Set<LessonProgramResponse> getLessonProgramByTeacherId(Long teacherId){
        return createLessonProgramResponseForTeacher(lessonProgramRepository.getLessonProgramByTeachersId(teacherId));
    }

    public Set<LessonProgramResponse> getLessonProgramByStudentId(Long studentId){
        return createLessonProgramResponseForStudent(lessonProgramRepository.getLessonProgramByStudentsId(studentId));
    }

    public Optional<LessonProgram> getLessonProgramById(Long id){
        return lessonProgramRepository.findById(id);
    }

    /*
    public ResponseMessage deleteTeachersLessonProgram(Long teacherId,Long lessonProgramId){

    }

    public ResponseMessage deleteStudentsLessonProgram(Long studentId,Long lessonProgramId){
        List<LessonProgram>
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
                .day(l.getDay())
                .startTime(l.getStartTime())
                .stopTime(l.getStopTime())
                .lessonProgramId(l.getId())
                .lessonName(l.getLesson())
                .build()).collect(Collectors.toSet());
    }
    public Set<LessonProgramResponse> createLessonProgramResponseForTeacher(List<LessonProgram> lessonProgram){
        return lessonProgram.stream().map((l)-> LessonProgramResponse.builder()
                .day(l.getDay())
                .startTime(l.getStartTime())
                .stopTime(l.getStopTime())
                .lessonProgramId(l.getId())
                .lessonName(l.getLesson())
                .students(l.getStudents().stream().map(createResponseObjectService::createStudentResponse).collect(Collectors.toSet()))
                .build()).collect(Collectors.toSet());
    }

    public Set<LessonProgramResponse> createLessonProgramResponseForStudent(List<LessonProgram> lessonProgram){
        return lessonProgram.stream().map((l)-> LessonProgramResponse.builder()
                .day(l.getDay())
                .startTime(l.getStartTime())
                .stopTime(l.getStopTime())
                .lessonProgramId(l.getId())
                .lessonName(l.getLesson())
                .teachers(l.getTeachers().stream().map(createResponseObjectService::createTeacherResponse).collect(Collectors.toSet()))
                .build()).collect(Collectors.toSet());
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
}
