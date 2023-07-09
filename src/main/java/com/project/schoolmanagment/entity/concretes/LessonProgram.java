package com.project.schoolmanagment.entity.concretes;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.schoolmanagment.entity.enums.Day;
import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LessonProgram {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private Day day;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "US")
	private LocalTime startTime;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "US")
	private LocalTime stopTime;

	@JsonIgnore
	@ManyToMany
	@JoinTable(
			name = "lesson_program_lesson",
			joinColumns = @JoinColumn(name = "lessonprogram_id"),
			inverseJoinColumns = @JoinColumn(name = "lesson_id")
	)
	private Set<Lesson>lesson;

	@ManyToOne(cascade = CascadeType.PERSIST)
	private EducationTerm educationTerm;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@ManyToMany(mappedBy = "lessonsProgramList" ,fetch = FetchType.EAGER)
	private Set<Teacher> teachers;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@ManyToMany(mappedBy = "lessonsProgramList" ,fetch = FetchType.EAGER)
	private Set<Student>students;

	@PreRemove
	private void removeLessonProgramFromStudent(){
		teachers.forEach(teacher -> teacher.getLessonsProgramList().remove(this));
		students.forEach(student -> student.getLessonsProgramList().remove(this));
	}







}
