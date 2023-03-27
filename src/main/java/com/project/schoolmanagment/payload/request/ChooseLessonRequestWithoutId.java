package com.project.schoolmanagment.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ChooseLessonRequestWithoutId implements Serializable {

    @NotNull(message = "Please select lesson program   ")
    @Size(min = 1, message = "lessons must not empty")
    private Set<Long> lessonProgramId;

}
