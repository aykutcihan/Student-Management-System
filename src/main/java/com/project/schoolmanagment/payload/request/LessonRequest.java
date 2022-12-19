package com.project.schoolmanagment.payload.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonRequest implements Serializable {

    @NotEmpty
    private String lessonName;
}
