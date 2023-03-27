package com.project.schoolmanagment.payload.request.UpdateRequest;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UpdateTeacherRequest implements Serializable {

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Past
    private LocalDate birthDay;

    @NotNull
    private String ssn;
}
