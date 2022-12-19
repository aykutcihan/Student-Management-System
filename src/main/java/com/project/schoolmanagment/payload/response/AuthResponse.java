package com.project.schoolmanagment.payload.response;

import com.project.schoolmanagment.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AuthResponse{

    private String ssn;
    private String role;
    private String token;
}
