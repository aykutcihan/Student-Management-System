package com.project.schoolmanagment.payload.request.abstracts;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.schoolmanagment.entity.enums.Gender;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class BaseUserRequest implements Serializable {

    @NotEmpty
    private String name;

    @NotEmpty
    private String surname;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Past
    private LocalDate birthDay;

    @NotEmpty
    @Pattern(regexp="^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$")
    private String ssn;

    @NotEmpty
    private String birthPlace;

    @NotEmpty
    private String password;

    @NotEmpty(message = "Please enter your phone number")
    //@Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
      //      message = "Please enter valid phone number")
    //@Size(min = 10, max = 10, message = "Phone number should be exact 10 characters")
    private String phoneNumber;

    @NotNull
    private Gender gender;
}
