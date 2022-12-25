package com.project.schoolmanagment;

import com.project.schoolmanagment.entity.concretes.Admin;
import com.project.schoolmanagment.repository.AdminRepository;
import com.project.schoolmanagment.service.AdminService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SchoolManagementApplication implements CommandLineRunner {

    private final AdminService adminService;

    public SchoolManagementApplication(AdminService adminService) {
        this.adminService = adminService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SchoolManagementApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if(adminService.getAllAdmin().size()==0){
            adminService.save(new Admin("Admin","Admin123"));
        }
    }
}
