package com.project.schoolmanagment.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class StudentInfoRequest {


    @DecimalMax("100.0")
    @DecimalMin("0.0")
    @NotNull(message = "Please enter midterm exam")
    private Double midtermExam;

    @DecimalMax("100.0")
    @DecimalMin("0.0")
    @NotNull(message = "Please enter final exam")
    private Double finalExam;

    @NotNull(message = "Please enter absentee")
    private Integer absentee;

    @NotNull(message = "Please enter info")
    @Size(min = 10, max = 200, message = "Info should be at least 10 characters")
    private String infoNote;

    @NotNull(message = "Please select lesson")
    private Long lessonId;

    @NotNull(message = "Please select teacher")
    private Long teacherId;

    @NotNull(message = "Please select student")
    private Long studentId;
}
