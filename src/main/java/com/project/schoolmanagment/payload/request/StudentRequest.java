package com.project.schoolmanagment.payload.request;

import com.project.schoolmanagment.entity.concretes.AdvisorTeacher;
import com.project.schoolmanagment.entity.concretes.Lesson;
import com.project.schoolmanagment.entity.concretes.Parent;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class StudentRequest extends BaseUserRequest{

    @NotEmpty
    private String motherName;

    @NotEmpty
    private String fatherName;

    @NotEmpty
    private String studentNumber;

    //@NotNull
    //private Long parentId;

    //@NotNull
    //private Set<Long> lessonIdList;

    @NotNull
    private Long advisorTeacherId;
}
