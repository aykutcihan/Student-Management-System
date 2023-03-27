package com.project.schoolmanagment.payload.request;

import com.project.schoolmanagment.entity.concretes.EducationTerm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class StudentInfoRequestWithoutTeacherId {



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
    private String infoNote;

    @NotNull(message = "Please select lesson")
    private Long lessonId;

    @NotNull(message = "Please select student")
    private Long studentId;
}
