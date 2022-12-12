package com.project.schoolmanagment.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.schoolmanagment.entity.concretes.Lesson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LessonProgramResponse {


    private Long lessonProgramId;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime stopTime;

    private Set<Lesson> lessonName;

}
