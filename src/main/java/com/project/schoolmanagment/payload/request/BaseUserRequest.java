package com.project.schoolmanagment.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.schoolmanagment.entity.concretes.Role;
import com.project.schoolmanagment.entity.concretes.UserRole;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class BaseUserRequest {

    @NotEmpty
    private String name;

    @NotEmpty
    private String surname;

    @NotNull(message = "Please enter your Birth day")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDay;

    @NotEmpty
    private String ssn;

    @NotEmpty
    private String birthPlace;

    @NotEmpty
    private String password;

    @NotEmpty(message = "Please enter your phone number")
    //@Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
            //message = "Please enter valid phone number")
    //@Size(min = 10, max = 10, message = "Phone number should be exact 10 characters")
    private String phoneNumber;


}
