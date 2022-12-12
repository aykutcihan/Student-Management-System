package com.project.schoolmanagment.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AssistantManagerResponse {

    private Long assistantManagerId;
    private String name;
    private String surname;
    private String ssn;
    private String phoneNumber;
}
