package com.project.schoolmanagment.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.schoolmanagment.payload.request.abstracts.BaseUserRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
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

   @NotNull(message = "Please select lesson")
    @Size(min = 1, message = "lessons must not empty")
    private Set<Long> lessonsIdList;

   @NotNull(message = "Please select isAdvisor Teacher")
    @JsonProperty("isAdvisorTeacher")
    private boolean isAdvisorTeacher;

    @Email(message = "Please enter valid email")
    @Size(min = 5, max = 80)
    @NotNull(message = "Please enter your email")
    @Column(nullable = false, unique = true, length = 80)
    private String email;
}
