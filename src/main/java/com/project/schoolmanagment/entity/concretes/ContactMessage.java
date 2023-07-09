package com.project.schoolmanagment.entity.concretes;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
//TODO learn about serialization and de-serialization
public class ContactMessage implements Serializable {

	//TODO check all generation types and strategies
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//better to give a naming contactMessageId, contactMessageName, contactMessageSubject
	private Long id;

	@NotNull
	private String name;

	@NotNull
	private String email;

	@NotNull
	private String subject;

	@NotNull
	private String message;

	//2025-06-05
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate date;

}
