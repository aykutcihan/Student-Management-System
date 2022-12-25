package com.project.schoolmanagment.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.schoolmanagment.payload.request.abstracts.BaseUserRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class TeacherRequest extends BaseUserRequest {

    @NotNull
    @Size(min = 1, message = "lessons must not empty")
    private Set<Long> lessonsIdList;

    @NotNull
    @JsonProperty("isAdvisorTeacher")
    private boolean isAdvisorTeacher;

    @NotEmpty
    @Email
    private String email;
}
