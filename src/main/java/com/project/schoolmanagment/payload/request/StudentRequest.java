package com.project.schoolmanagment.payload.request;

import com.project.schoolmanagment.payload.request.abstracts.BaseUserRequest;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class StudentRequest extends BaseUserRequest {

    @NotNull
    private String motherName;

    @NotNull
    private String fatherName;

    @NotNull
    private String studentNumber;

    @NotNull
    @Email
    private String email;

    @NotNull
    private Long advisorTeacherId;

}
