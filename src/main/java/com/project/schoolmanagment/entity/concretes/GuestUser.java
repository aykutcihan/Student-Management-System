package com.project.schoolmanagment.entity.concretes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class GuestUser extends com.project.schoolmanagment.entity.abstracts.User {


}
