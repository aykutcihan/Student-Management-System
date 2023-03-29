package com.project.schoolmanagment.controller;

 import com.project.schoolmanagment.Exception.BadRequestException;
import com.project.schoolmanagment.entity.concretes.*;
import com.project.schoolmanagment.entity.enums.Role;
import com.project.schoolmanagment.payload.request.LoginRequest;
import com.project.schoolmanagment.payload.response.AuthResponse;
import com.project.schoolmanagment.repository.*;
import com.project.schoolmanagment.security.JwtUtils;
import com.project.schoolmanagment.security.service.UserDetailsImpl;
import com.project.schoolmanagment.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
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

        Student student = studentRepository.findByUsernameEquals(username);
        Teacher teacher = teacherRepository.findByUsernameEquals(username);
        Dean dean = deanRepository.findByUsernameEquals(username);
        ViceDean viceDean = viceDeanRepository.findByUsernameEquals(username);
        Admin admin = adminRepository.findByUsernameEquals(username);

        if (student != null) {
            if (!BCrypt.checkpw(password, student.getPassword())) throw new BadRequestException("Your password is wrong");
        } else if (teacher != null) {
            if (!BCrypt.checkpw(password, teacher.getPassword())) throw new BadRequestException("Your password is wrong");
        } else if (dean != null) {
            if (!BCrypt.checkpw(password, dean.getPassword())) throw new BadRequestException("Your password is wrong");
        } else if (viceDean != null) {
            if (!BCrypt.checkpw(password, viceDean.getPassword())) throw new BadRequestException("Your password is wrong");
        } else if (admin != null) {
            if (!BCrypt.checkpw(password, admin.getPassword())) throw new BadRequestException("Your password is wrong");
        } else
            throw new BadRequestException("User '" + username + "' not found");


        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

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
