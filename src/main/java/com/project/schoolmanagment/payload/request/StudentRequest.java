package com.project.schoolmanagment.payload.request;

import com.project.schoolmanagment.payload.request.abstracts.BaseUserRequest;
 
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class StudentRequest extends BaseUserRequest {

   @NotNull(message = "Please enter mother name")
    private String motherName;

   @NotNull(message = "Please enter father name")
    private String fatherName;

   @NotNull(message = "Please enter student number ")
    private String studentNumber;

   @NotNull(message = "Please enter email")
    @Email
    private String email;

   @NotNull(message = "Please select advisor teacher")
    private Long advisorTeacherId;

}
