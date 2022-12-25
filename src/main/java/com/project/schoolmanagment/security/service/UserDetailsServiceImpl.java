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
    //private final AdvisoryTeacherRepository advisoryTeacherRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String ssn) throws UsernameNotFoundException {
        Student student = studentRepository.getStudentBySsn(ssn);

        Teacher teacher = teacherRepository.getTeacherBySsn(ssn);
        Dean dean = deanRepository.getDeanBySsn(ssn);
        ViceDean viceDean = viceDeanRepository.getViceDeanBySsn(ssn);
        Admin admin = adminRepository.getAdminByUsername(ssn);
        //AdvisorTeacher advisorTeacher = advisoryTeacherRepository.getAdvisorTeacherByTeacher_Ssn(ssn);

        if (student != null) {
            return new UserDetailsImpl(student.getSsn(), student.getPassword(),student.getUserRole().getRole().name());
        }

        else if (teacher !=null) {
            return new UserDetailsImpl(teacher.getSsn(), teacher.getPassword(),teacher.getUserRole().getRole().name(),teacher.getIsAdvisor());
        }
        else if (dean !=null) {
            return new UserDetailsImpl(dean.getSsn(), dean.getPassword(), dean.getUserRole().getRole().name());
        }
        else if (viceDean !=null) {
            return new UserDetailsImpl(viceDean.getSsn(), viceDean.getPassword(), viceDean.getUserRole().getRole().name());
        }
        else if (admin!=null) {
            return new UserDetailsImpl(admin.getUsername(), admin.getPassword(),Role.ADMIN.name());
        }

        throw new UsernameNotFoundException("User '" + ssn + "' not found");
    }
}
