package com.project.schoolmanagment.payload.request.UpdateRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UpdateStudentInfoRequest {

    @DecimalMax("100.0") @DecimalMin("0.0")
    @NotNull(message = "Please enter midterm exam")
    private Double midtermExam;

    @DecimalMax("100.0") @DecimalMin("0.0")
    @NotNull(message = "Please enter final exam  ")
    private Double finalExam;

    @NotNull(message = "Please enter absentee ")
    private Integer absentee;

    @NotNull(message = "Please enter extra info  ")
    @Size( min = 6 , max = 250 ,message = "Extra Info should be at least 4 characters ")
    private String infoNote;

    @NotNull(message = "Please select lesson ")
    private Long lessonId;
    @NotNull(message = "Please select education term ")
    private Long educationTermId;
}
