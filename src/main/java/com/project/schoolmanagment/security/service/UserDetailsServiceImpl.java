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
    public UserDetails loadUserByUsername(String ssn) throws UsernameNotFoundException {
        Student student = studentRepository.getStudentBySsn(ssn);
        Teacher teacher = teacherRepository.getTeacherBySsn(ssn);
        Dean dean = deanRepository.getDeanBySsn(ssn);
        ViceDean viceDean = viceDeanRepository.getViceDeanBySsn(ssn);
        Admin admin = adminRepository.findBySsnEquals(ssn);

        if (student != null) {
            return new UserDetailsImpl(student.getSsn(), student.getPassword(), student.getUserRole().getRole().name(), false, student.getName());
        } else if (teacher != null) {
            return new UserDetailsImpl(teacher.getSsn(), teacher.getPassword(), teacher.getUserRole().getRole().name(), teacher.getIsAdvisor(), teacher.getName());
        } else if (dean != null) {
            return new UserDetailsImpl(dean.getSsn(), dean.getPassword(), dean.getUserRole().getRole().name(),false, dean.getName());
        } else if (viceDean != null) {
            return new UserDetailsImpl(viceDean.getSsn(), viceDean.getPassword(), viceDean.getUserRole().getRole().name(),false, viceDean.getName());
        } else if (admin != null) {
            return new UserDetailsImpl(admin.getSsn(), admin.getPassword(), Role.ADMIN.name(),false, admin.getName());
        }

        throw new UsernameNotFoundException("User '" + ssn + "' not found");
    }
}
