package com.project.schoolmanagment.payload.request;

import com.project.schoolmanagment.payload.request.abstracts.BaseUserRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class StudentRequest extends BaseUserRequest {

    @NotNull(message = "Please enter mother name")
    private String motherName;

    @NotNull(message = "Please enter father name")
    private String fatherName;

    @Email(message = "Please enter valid email")
    @Size(min = 5, max = 80)
    @NotNull(message = "Please enter your email")
    @Column(nullable = false, unique = true, length = 80)
    private String email;

    @NotNull(message = "Please select advisor teacher")
    private Long advisorTeacherId;

}
