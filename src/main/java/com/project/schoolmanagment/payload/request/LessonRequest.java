package com.project.schoolmanagment.payload.request;

 import lombok.*;

 import javax.validation.constraints.NotNull;
 import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonRequest implements Serializable {

    @NotNull(message = "Please enter lesson ")
    private String lessonName;
    @NotNull(message = "Please enter credit score")
    private int creditScore;
    @NotNull(message = "Please select isCompulsory")
    private boolean isCompulsory;
}
