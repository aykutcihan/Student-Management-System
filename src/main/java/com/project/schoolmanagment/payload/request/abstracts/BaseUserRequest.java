package com.project.schoolmanagment.payload.request.abstracts;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.schoolmanagment.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.UniqueElements;

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

    @NotNull(message = "Please enter your  username")
    private String username;

    @NotNull(message = "Please enter your name")
    private String name;

    @NotNull(message = "Please enter your surname")
    private String surname;

    @NotNull(message = "Please enter your birthday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Past
    private LocalDate birthDay;

    @NotNull
    @Pattern(regexp = "^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$" ,
            message = "Please enter valid SSN number")
    private String ssn;

    @NotNull(message = "Please enter your birth place")
    private String birthPlace;

    @NotNull(message = "Please enter your password")
    private String password;

    @NotNull(message = "Please enter your phone number")
    @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
            message = "Please enter valid phone number")
    @Size(min = 12, max = 12, message = "Phone number should be exact 12 characters")
    private String phoneNumber;

    @NotNull(message = "Please enter your gander")
    private Gender gender;
}
