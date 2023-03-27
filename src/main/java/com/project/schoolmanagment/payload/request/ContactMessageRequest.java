package com.project.schoolmanagment.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class ContactMessageRequest implements Serializable {


    @NotNull(message = "Please enter name")
    private String name;
    @NotNull(message = "Please enter email ")
    private String email;
    @NotNull(message = "Please enter subject")
    private String subject;
    @NotNull(message = "Please enter message ")
    private String message;


}
