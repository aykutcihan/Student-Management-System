package com.project.schoolmanagment.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.schoolmanagment.entity.concretes.LessonProgram;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentResponse {

    private Long studentId;
    private String name;
    private String surname;
    private String ssn;
    private String phoneNumber;
    private Set<LessonProgram> lessonProgramSet;
}
