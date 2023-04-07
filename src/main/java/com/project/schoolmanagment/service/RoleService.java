package com.project.schoolmanagment.service;

import com.project.schoolmanagment.Exception.ConflictException;
import com.project.schoolmanagment.entity.concretes.Role;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    public Role save(RoleType roleType) {
        if (roleRepository.existsByERoleEquals(roleType)) {
            throw new ConflictException("This role already register ");
        }
        Role role = Role.builder().roleType(roleType).build();
        return roleRepository.save(role);
    }

    public Role getUserRole(RoleType roleType) {
        Optional<Role> userRole = roleRepository.findByERoleEquals(roleType);
        return userRole.orElse(null);
    }

    public List<Role> getAllUserRole() {
        return roleRepository.findAll();
    }

}
