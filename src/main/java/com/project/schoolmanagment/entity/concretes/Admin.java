package com.project.schoolmanagment.entity.concretes;
import com.project.schoolmanagment.entity.abstracts.User;
import com.project.schoolmanagment.entity.enums.Gender;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Admin extends User {

}
