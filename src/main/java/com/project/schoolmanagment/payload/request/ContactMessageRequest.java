package com.project.schoolmanagment.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ContactMessageRequest implements Serializable {


    @NotNull(message = "Please enter name")
    private String name;

    @Email(message = "Please enter valid email")
    @Size(min = 5, max = 80)
    @NotNull(message = "Please enter your email")
    @Column(nullable = false, unique = true, length = 80)
    private String email;

    @NotNull(message = "Please enter subject")
    private String subject;

    @NotNull(message = "Please enter message ")
    private String message;


}
