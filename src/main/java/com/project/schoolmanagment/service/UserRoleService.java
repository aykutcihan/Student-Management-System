package com.project.schoolmanagment.service;

import com.project.schoolmanagment.entity.enums.Role;
import com.project.schoolmanagment.entity.concretes.UserRole;
import com.project.schoolmanagment.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRole getUserRole(Role role){
        Optional<UserRole> userRole = userRoleRepository.getUserRoleByRole(role);
        return userRole.orElse(null);
    }
}
