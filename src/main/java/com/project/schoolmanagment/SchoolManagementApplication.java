package com.project.schoolmanagment;

import com.project.schoolmanagment.entity.enums.Gender;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.payload.request.AdminRequest;
import com.project.schoolmanagment.service.AdminService;
import com.project.schoolmanagment.service.UserRoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@SpringBootApplication
public class SchoolManagementApplication implements CommandLineRunner {


    private final UserRoleService userRoleService;
    private final AdminService adminService;
    private final PasswordEncoder passwordEncoder;

    public SchoolManagementApplication(UserRoleService userRoleService, AdminService adminService, PasswordEncoder passwordEncoder) {
        this.userRoleService = userRoleService;
        this.adminService = adminService;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(SchoolManagementApplication.class, args);

    }

    @Override
    public void run(String... args) {
        if(userRoleService.getAllUserRole().isEmpty()){
            userRoleService.save(RoleType.ADMIN);
            userRoleService.save(RoleType.MANAGER);
            userRoleService.save(RoleType.ASSISTANT_MANAGER);
            userRoleService.save(RoleType.TEACHER);
            userRoleService.save(RoleType.STUDENT);
            userRoleService.save(RoleType.ADVISORY_TEACHER);
            userRoleService.save(RoleType.GUEST_USER);
        }

        if(adminService.countAllAdmins()==0){
            AdminRequest adminRequest  = new AdminRequest();
            adminRequest.setUsername("Admin");
            adminRequest.setSsn("987-99-9999");
            adminRequest.setPassword(passwordEncoder.encode("Ankara06*"));
            adminRequest.setName("Lars");
            adminRequest.setSurname("Urich");
            adminRequest.setPhoneNumber("555-444-4321");
            adminRequest.setGender(Gender.FEMALE);
            adminRequest.setBirthDay(LocalDate.of(1980,2,2));
            adminRequest.setBirthPlace("Texas");
            adminService.save(adminRequest);
        }


    }
}









