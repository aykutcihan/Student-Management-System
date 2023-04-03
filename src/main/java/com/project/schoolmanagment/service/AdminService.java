package com.project.schoolmanagment.service;

import com.project.schoolmanagment.Exception.ConflictException;
import com.project.schoolmanagment.entity.concretes.Admin;
import com.project.schoolmanagment.entity.enums.Role;
import com.project.schoolmanagment.payload.request.AdminRequest;
import com.project.schoolmanagment.payload.response.AdminResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.repository.*;
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

    private final StudentRepository studentRepository;
    private final ViceDeanRepository viceDeanRepository;
    private final DeanRepository deanRepository;
    private final AdminRepository adminRepository;
    private final TeacherRepository teacherRepository;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;


    public ResponseMessage save(AdminRequest request) {

        if (adminRepository.existsByUsername(request.getUsername())) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_USERNAME, request.getUsername()));
        } else
            checkDuplicate(request.getUsername(), request.getSsn(), request.getPhoneNumber());

        Admin admin = createAdminForSave(request);
        admin.setUserRole(userRoleService.getUserRole(Role.ADMIN));
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        Admin savedData = adminRepository.save(admin);
        return ResponseMessage.<AdminResponse>builder()
                .message("Admin Saved")
                .httpStatus(HttpStatus.CREATED)
                .object(createResponse(savedData)).build();
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

    protected Admin createAdminForSave(AdminRequest request) {
        return Admin.builder()
                .username(request.getUsername())
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
                .username(request.getUsername())
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
                .username(admin.getUsername())
                .name(admin.getName())
                .surname(admin.getSurname())
                .phoneNumber(admin.getPhoneNumber())
                .gender(admin.getGender())
                .ssn(admin.getSsn()).build();


    }

    public void checkDuplicateWithEmail(String username, String ssn, String phone, String email) {

        if (adminRepository.existsByUsername(username)  ||
                deanRepository.existsByUsername(username)  ||
                studentRepository.existsByUsername(username)  ||
                teacherRepository.existsByUsername(username)  ||
                viceDeanRepository.existsByUsername(username)
        ) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_USERNAME, username));
        } else if (adminRepository.existsBySsn(ssn)  ||
                deanRepository.existsBySsn(ssn)  ||
                studentRepository.existsBySsn(ssn)  ||
                teacherRepository.existsBySsn(ssn)  ||
                viceDeanRepository.existsBySsn(ssn)
        ) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_SSN, ssn));
        } else if (adminRepository.existsByPhoneNumber(phone)  ||
                deanRepository.existsByPhoneNumber(phone)  ||
                studentRepository.existsByPhoneNumber(phone)  ||
                teacherRepository.existsByPhoneNumber(phone)  ||
                viceDeanRepository.existsByPhoneNumber(phone)
        ) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_PHONE_NUMBER, phone));
        } else if (studentRepository.existsByEmail(phone)  ||
                teacherRepository.existsByEmail(phone)
        ) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_SSN, email));
        }
    }

    public void checkDuplicate(String username, String ssn, String phone) {

        if (adminRepository.existsByUsername(username)  ||
                deanRepository.existsByUsername(username)  ||
                studentRepository.existsByUsername(username)  ||
                teacherRepository.existsByUsername(username)  ||
                viceDeanRepository.existsByUsername(username)
        ) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_USERNAME, username));
        } else if (adminRepository.existsBySsn(ssn)  ||
                deanRepository.existsBySsn(ssn)  ||
                studentRepository.existsBySsn(ssn)  ||
                teacherRepository.existsBySsn(ssn)  ||
                viceDeanRepository.existsBySsn(ssn)
        ) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_SSN, ssn));
        } else if (adminRepository.existsByPhoneNumber(phone)  ||
                deanRepository.existsByPhoneNumber(phone)  ||
                studentRepository.existsByPhoneNumber(phone)  ||
                teacherRepository.existsByPhoneNumber(phone)  ||
                viceDeanRepository.existsByPhoneNumber(phone)
        ) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_PHONE_NUMBER, phone));
        }
    }

}
