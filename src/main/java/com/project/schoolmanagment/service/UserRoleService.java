package com.project.schoolmanagment.service;

import com.project.schoolmanagment.Exception.ConflictException;
import com.project.schoolmanagment.entity.concretes.UserRole;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;
    public UserRole save(RoleType roleType) {
        if (userRoleRepository.existsByERoleEquals(roleType)) {
            throw new ConflictException("This role already register ");
        }
        UserRole userRole = UserRole.builder().roleType(roleType).build();
        return userRoleRepository.save(userRole);
    }

    public UserRole getUserRole(RoleType roleType) {
        Optional<UserRole> userRole = userRoleRepository.findByERoleEquals(roleType);
        return userRole.orElse(null);
    }

    public List<UserRole> getAllUserRole() {
        return userRoleRepository.findAll();
    }

}
