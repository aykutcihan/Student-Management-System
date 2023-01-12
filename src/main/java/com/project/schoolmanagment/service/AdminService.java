package com.project.schoolmanagment.service;

import com.project.schoolmanagment.Exception.ConflictException;
import com.project.schoolmanagment.entity.concretes.Admin;
import com.project.schoolmanagment.entity.enums.Role;
import com.project.schoolmanagment.repository.AdminRepository;
import com.project.schoolmanagment.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final UserRoleService userRoleService;

    private final PasswordEncoder passwordEncoder;

    public Admin save(Admin admin) {
        if (adminRepository.existsByUsername(admin.getUsername().trim())) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_SSN, admin.getUsername()));
        }
        admin.setRole(userRoleService.getUserRole(Role.ADMIN));
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminRepository.save(admin);
    }

    public long countAllAdmin() {
        return adminRepository.count();
    }

    public Page<Admin> getAllAdmin(Pageable pageable) {
        return adminRepository.findAll(pageable);
    }

    public String deleteAdmin(Long id) {
        Optional<Admin> admin = adminRepository.findById(id);
        if (admin.isPresent()) {
            adminRepository.deleteById(id);
            return "Admin deleted Successful";
        }
        return Messages.NOT_FOUND_USER_MESSAGE;
    }
}
