package com.project.schoolmanagment.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class TeacherRequest extends BaseUserRequest{

    @NotNull
    private Set<Long> lessons;

    @NotNull
    @JsonProperty("isAdvisorTeacher")
    private boolean isAdvisorTeacher;
}
