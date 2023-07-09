package com.project.schoolmanagment.entity.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.schoolmanagment.entity.enums.Note;
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
	/**
	 * number of absentees of the student in the lessons
	 */
	private Integer absentee;

	private Double midtermExam;

	private Double finalExam;

	private Double examAverage;

	private String infoNote;

	@ManyToOne
	@JsonIgnore
	private Teacher teacher;

	@ManyToOne
	private Student student;

	@Enumerated(EnumType.STRING)
	private Note letterGrade;

	@ManyToOne
	@JsonIgnoreProperties("lesson")
	private Lesson lesson;

	@OneToOne
	private EducationTerm educationTerm;







}
