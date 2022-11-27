package com.project.schoolmanagment.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserResponse {

    private Long userId;
    private String name;
    private String surname;
    private String birthDay;
    private String birthPlace;
    private String ssn;
}
