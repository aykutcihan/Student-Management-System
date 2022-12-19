package com.project.schoolmanagment.entity.concretes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class StudentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double midtermExam;

    private Double finalExam;

    private Integer absentee;

    private String infoNote;

    private String lessonName;

    @ManyToOne
    @JsonIgnoreProperties("lessonsProgramList")
    private Teacher teacherId;

    @ManyToOne
    private Student studentId;
}
