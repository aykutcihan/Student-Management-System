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

    @NotEmpty
    private String motherName;

    @NotEmpty
    private String fatherName;

    @NotEmpty
    private String studentNumber;

    @NotEmpty
    @Email
    private String email;

    @NotNull
    private Long advisorTeacherId;

}
