package com.project.schoolmanagment.entity.concretes;

import com.project.schoolmanagment.entity.abstracts.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class Student extends User {

    @NotEmpty
    private String motherName;

    @NotEmpty
    private String fatherName;

    @NotEmpty
    private String studentNumber;

    @ManyToOne
    private Parent parent;

    @ManyToMany
    private Set<Lesson> lessons;

    @ManyToOne
    private AdvisorTeacher advisorTeacher;
}
