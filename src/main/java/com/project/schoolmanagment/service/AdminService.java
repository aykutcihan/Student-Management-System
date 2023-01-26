package com.project.schoolmanagment.service;

import com.project.schoolmanagment.Exception.ConflictException;
import com.project.schoolmanagment.entity.concretes.Admin;
import com.project.schoolmanagment.entity.enums.Role;
 import com.project.schoolmanagment.payload.request.AdminRequest;
import com.project.schoolmanagment.payload.response.AdminResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.repository.AdminRepository;
import com.project.schoolmanagment.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository repository;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;





    public ResponseMessage save(AdminRequest request) {

        if (repository.existsBySsn(request.getSsn())) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_SSN, request.getSsn()));

        } else if (repository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_PHONE_NUMBER, request.getPhoneNumber()));
        }
        Admin admin = createAdminForSave(request);
        admin.setUserRole(userRoleService.getUserRole(Role.ADMIN));
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        Admin savedData = repository.save(admin);
        return ResponseMessage.<AdminResponse>builder()
                .message("Admin Saved")
                .httpStatus(HttpStatus.CREATED)
                .object(createResponse(savedData)).build();
    }

    public long countAllAdmin() {
        return repository.count();
    }

    public Page<Admin> getAllAdmin(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public String deleteAdmin(Long id) {
        Optional<Admin> admin = repository.findById(id);
        if (admin.isPresent()) {
            repository.deleteById(id);
            return "Admin deleted Successful";
        }
        return Messages.NOT_FOUND_USER_MESSAGE;
    }

    protected Admin createAdminForSave(AdminRequest request) {
        return Admin.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .password(request.getPassword())
                .ssn(request.getSsn())
                .birthDay(request.getBirthDay())
                .birthPlace(request.getBirthPlace())
                .phoneNumber(request.getPhoneNumber())
                .gender(request.getGender())
                .build();
    }


    private Admin createUpdate(AdminRequest request, Long managerId) {
        return Admin.builder().id(managerId)
                .ssn(request.getSsn())
                .name(request.getName())
                .surname(request.getSurname())
                .birthPlace(request.getBirthPlace())
                .birthDay(request.getBirthDay())
                .phoneNumber(request.getPhoneNumber())
                .gender(request.getGender())
                .userRole(userRoleService.getUserRole(Role.ADMIN))
                .build();

    }

    private AdminResponse createResponse(Admin admin) {
        return AdminResponse.builder().userId(admin.getId())
                .name(admin.getName())
                .surname(admin.getSurname())
                .phoneNumber(admin.getPhoneNumber())
                .gender(admin.getGender())
                .ssn(admin.getSsn()).build();
    }

}
