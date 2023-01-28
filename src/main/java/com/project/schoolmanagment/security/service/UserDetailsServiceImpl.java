package com.project.schoolmanagment.security.service;


import com.project.schoolmanagment.entity.concretes.*;
import com.project.schoolmanagment.entity.enums.Role;
import com.project.schoolmanagment.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final DeanRepository deanRepository;
    private final ViceDeanRepository viceDeanRepository;
    private final AdminRepository adminRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = studentRepository.findByUsernameEquals(username);
        Teacher teacher = teacherRepository.findByUsernameEquals(username);
        Dean dean = deanRepository.findByUsernameEquals(username);
        ViceDean viceDean = viceDeanRepository.findByUsernameEquals(username);
        Admin admin = adminRepository.findByUsernameEquals(username);

        if (student != null) {
            return new UserDetailsImpl(student.getUsername(), student.getPassword(), student.getUserRole().getRole().name(), false, student.getName());
        } else if (teacher != null) {
            return new UserDetailsImpl(teacher.getUsername(), teacher.getPassword(), teacher.getUserRole().getRole().name(), teacher.getIsAdvisor(), teacher.getName());
        } else if (dean != null) {
            return new UserDetailsImpl(dean.getUsername(), dean.getPassword(), dean.getUserRole().getRole().name(), false, dean.getName());
        } else if (viceDean != null) {
            return new UserDetailsImpl(viceDean.getUsername(), viceDean.getPassword(), viceDean.getUserRole().getRole().name(), false, viceDean.getName());
        } else if (admin != null) {
            return new UserDetailsImpl(admin.getUsername(), admin.getPassword(), Role.ADMIN.name(), false, admin.getName());
        }

        throw new UsernameNotFoundException("User '" + username + "' not found");
    }
}
