package com.project.schoolmanagment.payload.request;

 import lombok.*;

 import javax.validation.constraints.NotNull;
 import javax.validation.constraints.Pattern;
 import javax.validation.constraints.Size;
 import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonRequest implements Serializable {

    @NotNull(message = "Please enter lesson ")
    @Size(min = 2, max = 16, message = "Lesson name should be at least 2 characters")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+" ,message="Lesson name must consist of the characters .")
    private String lessonName;
    @NotNull(message = "Please enter credit score")
    private int creditScore;
    @NotNull(message = "Please select isCompulsory")
    private boolean isCompulsory;
}
