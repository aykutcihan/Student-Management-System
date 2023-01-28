package com.project.schoolmanagment.payload.response.abstracts;

import com.project.schoolmanagment.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class BaseUserResponse {

    private Long userId;
    private String username;
    private String name;
    private String surname;
    private String ssn;
    private String phoneNumber;
    private Gender gender;
}
