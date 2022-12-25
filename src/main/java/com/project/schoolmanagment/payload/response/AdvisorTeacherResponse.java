package com.project.schoolmanagment.payload.response;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AdvisorTeacherResponse {

    private Long advisorTeacherId;
    private String teacherName;
    private String teacherSSN;
    private String teacherSurname;
}
