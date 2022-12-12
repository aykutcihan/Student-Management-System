package com.project.schoolmanagment.entity.concretes;

import com.project.schoolmanagment.entity.abstracts.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Student extends User {

    private String motherName;

    private String fatherName;

    private String studentNumber;

    @ManyToOne
    private Parent parent;

    //@ManyToMany
    //private Set<Lesson> lessons;

    @ManyToOne
    private AdvisorTeacher advisorTeacher;


    @ManyToMany
    private Set<LessonProgram> lessonProgramList;
}
