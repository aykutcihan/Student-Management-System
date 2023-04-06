package com.project.schoolmanagment;

import com.project.schoolmanagment.entity.enums.Gender;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.payload.request.AdminRequest;
import com.project.schoolmanagment.service.AdminService;
import com.project.schoolmanagment.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class SchoolManagementApplication implements CommandLineRunner {

    private final AdminService adminService;
    private final RoleService roleService;

    public SchoolManagementApplication(AdminService adminService, RoleService roleService) {

        this.adminService = adminService;
        this.roleService = roleService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SchoolManagementApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        if (roleService.getAllUserRole().size() == 0) {
            roleService.save(RoleType.ADMIN);
            roleService.save(RoleType.MANAGER);
            roleService.save(RoleType.ASSISTANTMANAGER);
            roleService.save(RoleType.TEACHER);
            roleService.save(RoleType.STUDENT);
            roleService.save(RoleType.ADVISORTEACHER);
            roleService.save(RoleType.GUESTUSER);
        }
        if (adminService.countAllAdmin() == 0) {
            AdminRequest admin = new AdminRequest();
            admin.setUsername("Admin");
            admin.setSsn("987-99-9999");
            admin.setPassword("485424698");
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









