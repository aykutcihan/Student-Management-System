package com.project.schoolmanagment.payload.request;

 import lombok.*;

 import javax.validation.constraints.NotNull;
 import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonRequest implements Serializable {

    @NotNull
    private String lessonName;
    @NotNull
    private int creditScore;

    private boolean isCompulsory;
}
