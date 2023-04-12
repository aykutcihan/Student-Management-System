package com.project.schoolmanagment.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.schoolmanagment.entity.enums.Term;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class EducationTermResponse implements Serializable {

    private Long id;

    private Term term;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDate lastRegistrationDate;
}
