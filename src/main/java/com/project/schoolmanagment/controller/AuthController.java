package com.project.schoolmanagment.controller;

 import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.payload.request.LoginRequest;
import com.project.schoolmanagment.payload.response.AuthResponse;
import com.project.schoolmanagment.repository.*;
import com.project.schoolmanagment.security.jwt.JwtUtils;
import com.project.schoolmanagment.security.service.UserDetailsImpl;
 import lombok.RequiredArgsConstructor;
 import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
 import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
 import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    public final AuthenticationManager authenticationManager;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final DeanRepository deanRepository;
    private final ViceDeanRepository viceDeanRepository;
    private final AdminRepository adminRepository;
    public final JwtUtils jwtUtils;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(
            @RequestBody @Valid LoginRequest loginRequest
    ) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = "Bearer " + jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Set<String> roles = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        Optional<String> role = roles.stream().findFirst();

        AuthResponse.AuthResponseBuilder authResponse = AuthResponse.builder();
        authResponse.username(userDetails.getUsername());
        authResponse.token(token);
        authResponse.name(userDetails.getUsername());
        if (role.isPresent()) {
            authResponse.role(role.get());
            if (role.get().equalsIgnoreCase(RoleType.TEACHER.name())) {
                authResponse.isAdvisor(userDetails.getIsAdvisor().toString());
            }
        }
        return ResponseEntity.ok(authResponse.build());


    }

}
