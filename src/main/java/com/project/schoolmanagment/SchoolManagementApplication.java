package com.project.schoolmanagment;

import com.project.schoolmanagment.entity.concretes.Admin;
import com.project.schoolmanagment.entity.enums.Role;
import com.project.schoolmanagment.service.AdminService;
import com.project.schoolmanagment.service.UserRoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
        }
        if (adminService.countAllAdmin() == 0) {
            adminService.save(new Admin("Admin", "Admin123"));
        }
    }
}
