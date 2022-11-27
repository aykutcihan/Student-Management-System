package com.project.schoolmanagment.service;

import com.project.schoolmanagment.entity.concretes.Lesson;
import com.project.schoolmanagment.entity.concretes.Student;
import com.project.schoolmanagment.entity.concretes.Teacher;
import com.project.schoolmanagment.payload.Dto.StudentRequestDto;
import com.project.schoolmanagment.payload.request.StudentRequest;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentRequestDto studentRequestDto;

    private final LessonService lessonService;

    public ResponseMessage<Student> save(StudentRequest studentRequest){
        if(studentRepository.existsBySsn(studentRequest.getSsn())){
            return ResponseMessage.<Student>builder()
                    .message("This student already register").build();
        }

        Set<Lesson> lessons = getLessonsByLessonId(studentRequest.getLessonIdList());
        Student student = studentRequestToDto(studentRequest);
        student.setLessons(lessons);
        //student.setParent(); from parent service
        //student.setAdvisorTeacher(); from advisor teacher service
        return ResponseMessage.<Student>builder().object(studentRepository.save(student))
                .message("Student saved successfully").build();
    }

    private Student studentRequestToDto(StudentRequest studentRequest){
       return studentRequestDto.dtoStudent(studentRequest);
    }

    private Set<Lesson> getLessonsByLessonId(Set<Long> idList) {
        return lessonService.getLessonByLessonNameList(idList);
    }


}
