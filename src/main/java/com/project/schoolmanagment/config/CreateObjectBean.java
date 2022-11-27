package com.project.schoolmanagment.config;

import com.project.schoolmanagment.payload.Dto.StudentRequestDto;
import com.project.schoolmanagment.payload.Dto.TeacherRequestDto;
import com.project.schoolmanagment.payload.request.TeacherRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateObjectBean {

    @Bean
    public TeacherRequestDto teacherRequestDto(){
        return new TeacherRequestDto();
    }
    @Bean
    public StudentRequestDto studentRequestDto(){
        return new StudentRequestDto();
    }
}
