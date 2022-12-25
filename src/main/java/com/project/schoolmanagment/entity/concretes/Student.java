package com.project.schoolmanagment.entity.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.schoolmanagment.entity.abstracts.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
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

    private String studentNumber;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private AdvisorTeacher advisorTeacher;


    @ManyToMany
    @JoinTable(
            name = "student_lessonprogram",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_program_id"))
    private Set<LessonProgram> lessonsProgramList;

    @OneToMany(mappedBy = "student",cascade = CascadeType.REMOVE)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Meet> meets;

    private String email;

    @OneToMany(mappedBy = "studentId",cascade = CascadeType.REMOVE)
    private List<StudentInfo> studentInfoList;

}
