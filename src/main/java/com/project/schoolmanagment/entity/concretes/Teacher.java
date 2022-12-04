package com.project.schoolmanagment.entity.concretes;

import com.project.schoolmanagment.entity.abstracts.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Teacher extends User {

    @ManyToMany
    private Set<Lesson> lessons;

    @Transient
    private boolean isAdvisorTeacher;

}
