package com.project.schoolmanagment.payload.request;

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
    Set<Long> lessons;
}
