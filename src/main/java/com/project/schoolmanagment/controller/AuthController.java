package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.entity.enums.Role;
import com.project.schoolmanagment.payload.request.AdminRequest;
import com.project.schoolmanagment.payload.request.LoginRequest;
import com.project.schoolmanagment.payload.response.AuthResponse;
import com.project.schoolmanagment.security.JwtUtils;
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

    public final JwtUtils jwtUtils;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(
            @RequestBody @Valid LoginRequest loginRequest
    ) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = "Bearer " + jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        Set<String> roles = user
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        Optional<String> role = roles.stream().findFirst();
        AuthResponse.AuthResponseBuilder authResponse = AuthResponse.builder();
        authResponse.username(user.getUsername());
        authResponse.token(token);
        authResponse.name(user.getName());
        if (role.isPresent()) {
            authResponse.role(role.get());
            if (role.get().equalsIgnoreCase(Role.TEACHER.name())) {
                authResponse.isAdvisor(user.getIsAdvisor().toString());
            }
        }
        return ResponseEntity.ok(authResponse.build());
    }

}
