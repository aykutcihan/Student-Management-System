package com.project.schoolmanagment.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.NamedEntityGraph;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TeacherResponse {

    private Long teacherId;
    private String name;
    private String surname;
    private String ssn;
    private List<String> lessonsName;
}
