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

    @NotNull(message = "Please enter name ")
    private String name;

    @NotNull(message = "Please enter surname")
    private String surname;

    @NotNull(message = "Please enter birthday ")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Past
    private LocalDate birthDay;

    @NotNull(message = "Please enter ssn")
    private String ssn;
}
