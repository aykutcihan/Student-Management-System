package com.project.schoolmanagment.entity.concretes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Meet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime stopTime;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonIgnoreProperties({"teacher"})
    private AdvisorTeacher advisorTeacher;

    @ManyToOne
    @JsonIgnoreProperties({"lessonsProgramList","advisorTeacher"})
    private Student student;
}
