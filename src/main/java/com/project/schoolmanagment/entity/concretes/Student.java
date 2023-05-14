package com.project.schoolmanagment.entity.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.schoolmanagment.entity.abstracts.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true)
public class Student extends User {

    private String motherName;

    private String fatherName;

    private int studentNumber;
    @JsonIgnore //https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
    @ManyToOne(cascade = CascadeType.PERSIST)
    private AdvisorTeacher advisorTeacher;

    @JsonIgnore //https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
    @ManyToMany
    @JoinTable(
            name = "student_lessonprogram",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_program_id"))
    private Set<LessonProgram> lessonsProgramList;


    @JsonIgnore //https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
    @ManyToMany()
    @JoinTable(
            name = "meet_student_table",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "meet_id"))
    private List<Meet> meetList;

    @Column(unique = true)
    private String email;

    @JsonIgnore //https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
    @OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE)
    private List<StudentInfo> studentInfos;


    private boolean isActive;
}
