package com.project.schoolmanagment.config;

import com.project.schoolmanagment.payload.Dto.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateObjectBean {

    @Bean
    public TeacherRequestDto teacherRequestDto() {
        return new TeacherRequestDto();
    }

    @Bean
    public StudentRequestDto studentRequestDto() {
        return new StudentRequestDto();
    }

    @Bean
    public LessonProgramDto lessonProgramRequestDto() {
        return new LessonProgramDto();
    }

    @Bean
    public ViceDeanDto viceDeanDto() {
        return new ViceDeanDto();
    }

    @Bean
    public DeanDto DeanDto() {
        return new DeanDto();
    }

    @Bean
    public MeetDto meetDto() {
        return new MeetDto();
    }
}
