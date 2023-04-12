package com.project.schoolmanagment.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.schoolmanagment.payload.request.abstracts.BaseUserRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class TeacherRequest extends BaseUserRequest {

    @NotNull(message = "Please select lesson")
    private Set<Long> lessonsIdList;

    @NotNull(message = "Please select isAdvisor Teacher")
    @JsonProperty("isAdvisorTeacher")
    private boolean isAdvisorTeacher;

    @Email(message = "Please enter valid email")
    @NotNull(message = "Please enter your email")
    @Size(min = 5, max = 20, message = "Your email should be at least 5 characters")
    @Column(nullable = false, unique = true, length = 20)
    private String email;
}
