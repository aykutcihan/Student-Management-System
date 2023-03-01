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

    @DecimalMax("100.0") @DecimalMin("0.0")
    @NotNull
    private Double midtermExam;

    @DecimalMax("100.0") @DecimalMin("0.0")
    @NotNull
    private Double finalExam;

    @NotNull
    private Integer absentee;

    @NotEmpty
    private String infoNote;

    @NotNull
    private Long lessonId;
    @NotNull
    private Long educationTermId;

    @NotNull
    private Long studentId;
}
