package com.project.schoolmanagment.entity.concretes;

import com.project.schoolmanagment.entity.abstracts.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true)
public class Teacher extends User {

    @ManyToMany
    @JoinTable(
            name = "teacher_lessonprogram",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_program_id"))
    private Set<LessonProgram> lessonsProgramList;

    @OneToOne(mappedBy = "teacher",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private AdvisorTeacher advisorTeacher;

    @Column(name = "isAdvisor")
    private Boolean isAdvisor;

    private String email;

    @OneToMany(mappedBy = "teacherId",cascade = CascadeType.REMOVE)
    private List<StudentInfo> studentInfoList;

}
