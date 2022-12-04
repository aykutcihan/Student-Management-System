package com.project.schoolmanagment.service;

import com.project.schoolmanagment.entity.concretes.AdvisorTeacher;
import com.project.schoolmanagment.entity.concretes.Teacher;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.repository.AdvisoryTeacherRepository;
import com.project.schoolmanagment.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdvisorTeacherService {

    private final AdvisoryTeacherRepository advisoryTeacherRepository;

    public ResponseMessage<AdvisorTeacher> updateAdvisorTeacher(AdvisorTeacher advisorTeacher){
        return null;
    }
    public void saveAdvisorTeacher(Teacher teacher){
        AdvisorTeacher advisorTeacher = AdvisorTeacher.builder().teacher(teacher).build();
        advisoryTeacherRepository.save(advisorTeacher);
    }

    public List<AdvisorTeacher> getAllAdvisorTeacher(){
        //response obje oluşturulacak
        return advisoryTeacherRepository.findAll();
    }

    public AdvisorTeacher getAdvisorTeacherById(Long id){
        Optional<AdvisorTeacher> advisorTeacher = advisoryTeacherRepository.findById(id);
        return advisorTeacher.orElse(null);
    }
    public boolean checkAdvisorTeacher(Long id){
        return advisoryTeacherRepository.existsById(id);
    }

    public String deleteAdvisorTeacher(Long id) {
        Optional<AdvisorTeacher> advisorTeacher = advisoryTeacherRepository.findById(id);
        if (advisorTeacher.isPresent()) {
            advisoryTeacherRepository.deleteById(advisorTeacher.get().getId());
            return "Teacher deleted Successful";
        }
        return Messages.NOT_FOUND_USER_MESSAGE;
    }
}
