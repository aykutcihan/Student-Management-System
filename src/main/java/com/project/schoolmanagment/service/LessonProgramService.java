package com.project.schoolmanagment.service;

import com.project.schoolmanagment.Exception.BadRequestException;
import com.project.schoolmanagment.Exception.ResourceNotFoundException;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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

    public ResponseMessage<LessonProgramResponse> save(LessonProgramRequest lessonProgramRequest) {
        Set<Lesson> lessons = lessonService.getLessonByLessonNameList(lessonProgramRequest.getLessonIdList());
        if (lessons.size() == 0) {
            throw new ResourceNotFoundException(Messages.NOT_FOUND_LESSON_IN_LIST);
        } else if (TimeControl.check(lessonProgramRequest.getStartTime(), lessonProgramRequest.getStopTime())) {
            throw new BadRequestException(Messages.TIME_NOT_VALID_MESSAGE);
        }
        LessonProgram lessonProgram = lessonProgramRequestToDto(lessonProgramRequest, lessons);
        LessonProgram savedLessonProgram = lessonProgramRepository.save(lessonProgram);
        return ResponseMessage.<LessonProgramResponse>builder()
                .message("Created Lesson Program")
                .object(createLessonProgramResponseForSaveMethod(savedLessonProgram))
                .httpStatus(HttpStatus.CREATED).build();
    }

    public List<LessonProgramResponse> getAllLessonProgram() {
        return lessonProgramRepository.findAll()
                .stream()
                .map(this::createLessonProgramResponse)
                .collect(Collectors.toList());
    }

    public Set<LessonProgramResponse> getLessonProgramByTeacher(String username) {
        return lessonProgramRepository.getLessonProgramByTeacherUsername(username)
                .stream()
                .map(this::createLessonProgramResponseForTeacher)
                .collect(Collectors.toSet());
    }

    public Set<LessonProgramResponse> getLessonProgramByStudent(String username) {
        return lessonProgramRepository.getLessonProgramByStudentUsername(username)
                .stream()
                .map(this::createLessonProgramResponseForStudent)
                .collect(Collectors.toSet());
    }

    private LessonProgram lessonProgramRequestToDto(LessonProgramRequest lessonProgramRequest, Set<Lesson> lessons) {
        return lessonProgramDto.dtoLessonProgram(lessonProgramRequest, lessons);
    }

    public Set<LessonProgram> getLessonProgramById(Set<Long> lessonProgramIdList) {
        return lessonProgramRepository.getLessonProgramByLessonProgramIdList(lessonProgramIdList);
    }

    public LessonProgramResponse createLessonProgramResponseForSaveMethod(LessonProgram lessonProgram) {
        return LessonProgramResponse.builder()
                .day(lessonProgram.getDay())
                .startTime(lessonProgram.getStartTime())
                .stopTime(lessonProgram.getStopTime())
                .lessonProgramId(lessonProgram.getId())
                .lessonName(lessonProgram.getLesson())
                .build();
    }

    public LessonProgramResponse createLessonProgramResponse(LessonProgram lessonProgram) {
        return LessonProgramResponse.builder()
                .day(lessonProgram.getDay())
                .startTime(lessonProgram.getStartTime())
                .stopTime(lessonProgram.getStopTime())
                .lessonProgramId(lessonProgram.getId())
                .lessonName(lessonProgram.getLesson())
                .students(lessonProgram.getStudents().stream().map(createResponseObjectService::createStudentResponse).collect(Collectors.toSet()))
                .teachers(lessonProgram.getTeachers().stream().map(createResponseObjectService::createTeacherResponse).collect(Collectors.toSet()))
                .build();
    }

    public LessonProgramResponse createLessonProgramResponseForTeacher(LessonProgram lessonProgram) {
        return LessonProgramResponse.builder()
                .day(lessonProgram.getDay())
                .startTime(lessonProgram.getStartTime())
                .stopTime(lessonProgram.getStopTime())
                .lessonProgramId(lessonProgram.getId())
                .lessonName(lessonProgram.getLesson())
                .students(lessonProgram.getStudents().stream().map(createResponseObjectService::createStudentResponse).collect(Collectors.toSet()))
                .build();
    }

    public LessonProgramResponse createLessonProgramResponseForStudent(LessonProgram lessonProgram) {
        return LessonProgramResponse.builder()
                .day(lessonProgram.getDay())
                .startTime(lessonProgram.getStartTime())
                .stopTime(lessonProgram.getStopTime())
                .lessonProgramId(lessonProgram.getId())
                .lessonName(lessonProgram.getLesson())
                .teachers(lessonProgram.getTeachers().stream().map(createResponseObjectService::createTeacherResponse).collect(Collectors.toSet()))
                .build();
    }

    public Page<LessonProgramResponse> search(int page, int size, String sort, String type) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }

        return lessonProgramRepository.findAll(pageable).map(this::createLessonProgramResponse);
    }

    public ResponseMessage deleteLessonProgram(Long id) {
        Optional<LessonProgram> lessonProgram = lessonProgramRepository.findById(id);
        if (!lessonProgram.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_LESSON_MESSAGE, id));
        }
        lessonProgramRepository.deleteById(id);
        return ResponseMessage.builder().message("Lesson Program Deleted")
                .httpStatus(HttpStatus.OK)
                .build();
    }


}
