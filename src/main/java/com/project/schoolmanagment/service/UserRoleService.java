package com.project.schoolmanagment.service;

import com.project.schoolmanagment.Exception.ConflictException;
import com.project.schoolmanagment.entity.enums.Role;
import com.project.schoolmanagment.entity.concretes.UserRole;
import com.project.schoolmanagment.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;
    public UserRole save(Role role) {
        if (userRoleRepository.existsByRole(role)) {
            throw new ConflictException("This role already register");
        }
        UserRole userRole = UserRole.builder().role(role).build();
        return userRoleRepository.save(userRole);
    }

    public UserRole getUserRole(Role role) {
        Optional<UserRole> userRole = userRoleRepository.getUserRoleByRole(role);
        return userRole.orElse(null);
    }

    public List<UserRole> getAllUserRole() {
        return userRoleRepository.findAll();
    }
}
