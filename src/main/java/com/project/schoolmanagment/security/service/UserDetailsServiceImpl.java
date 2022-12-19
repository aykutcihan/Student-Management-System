package com.project.schoolmanagment.security.service;


import com.project.schoolmanagment.entity.concretes.*;
import com.project.schoolmanagment.entity.enums.Role;
import com.project.schoolmanagment.repository.*;
import com.project.schoolmanagment.service.AdvisorTeacherService;
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
    private final ManagerRepository managerRepository;
    private final AssistantManagerRepository assistantManagerRepository;
    private final AdminRepository adminRepository;
    //private final AdvisoryTeacherRepository advisoryTeacherRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String ssn) throws UsernameNotFoundException {
        Student student = studentRepository.getStudentBySsn(ssn);

        Teacher teacher = teacherRepository.getTeacherBySsn(ssn);
        Manager manager = managerRepository.getManagerBySsn(ssn);
        AssistantManager assistantManager = assistantManagerRepository.getAssistantManagerBySsn(ssn);
        Admin admin = adminRepository.getAdminByUsername(ssn);
        //AdvisorTeacher advisorTeacher = advisoryTeacherRepository.getAdvisorTeacherByTeacher_Ssn(ssn);

        if (student != null) {
            return new UserDetailsImpl(student.getSsn(), student.getPassword(),student.getUserRole().getRole().name());
        }

        else if (teacher !=null) {
            return new UserDetailsImpl(teacher.getSsn(), teacher.getPassword(),teacher.getUserRole().getRole().name());
        }
        else if (manager !=null) {
            return new UserDetailsImpl(manager.getSsn(), manager.getPassword(),manager.getUserRole().getRole().name());
        }
        else if (assistantManager!=null) {
            return new UserDetailsImpl(assistantManager.getSsn(), assistantManager.getPassword(),assistantManager.getUserRole().getRole().name());
        }
        else if (admin!=null) {
            return new UserDetailsImpl(admin.getUsername(), admin.getPassword(),Role.ADMIN.name());
        }

        throw new UsernameNotFoundException("User '" + ssn + "' not found");
    }
}
