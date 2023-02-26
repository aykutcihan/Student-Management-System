package com.project.schoolmanagment;

import com.project.schoolmanagment.entity.enums.Gender;
import com.project.schoolmanagment.entity.enums.Role;
import com.project.schoolmanagment.payload.request.AdminRequest;
import com.project.schoolmanagment.service.AdminService;
import com.project.schoolmanagment.service.UserRoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class SchoolManagementApplication implements CommandLineRunner {

    private final AdminService adminService;
    private final UserRoleService userRoleService;

    public SchoolManagementApplication(AdminService adminService, UserRoleService userRoleService) {

        this.adminService = adminService;
        this.userRoleService = userRoleService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SchoolManagementApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        if (userRoleService.getAllUserRole().size() == 0) {
            userRoleService.save(Role.ADMIN);
            userRoleService.save(Role.MANAGER);
            userRoleService.save(Role.ASSISTANTMANAGER);
            userRoleService.save(Role.TEACHER);
            userRoleService.save(Role.STUDENT);
            userRoleService.save(Role.ADVISORTEACHER);
            userRoleService.save(Role.GUESTUSER);
        }
        if (adminService.countAllAdmin() == 0) {
            AdminRequest admin = new AdminRequest();
            admin.setUsername("Admin");
            admin.setSsn("987-99-9999");
            admin.setPassword("Admin123");
            admin.setName("Admin");
            admin.setSurname("Admin");
            admin.setPhoneNumber("555-444-4321");
            admin.setGender(Gender.MALE);
            admin.setBirthDay(LocalDate.of(2002, 2, 2));
            admin.setBirthPlace("US");
            adminService.save(admin);
        }
    }
}









