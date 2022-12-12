package com.project.schoolmanagment.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.schoolmanagment.entity.concretes.Lesson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToMany;
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
public class LessonProgramRequest {

    @NotEmpty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotEmpty
    @JsonFormat(pattern = "hh:mm a")
    private LocalTime startTime;

    @NotEmpty
    @JsonFormat(pattern = "hh:mm a")
    private LocalTime stopTime;

    @NotNull
    @Size(min = 1, message = "lessons must not empty")
    private Set<Long> lessonIdList;

}
