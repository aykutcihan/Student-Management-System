package com.project.schoolmanagment.service;

import com.project.schoolmanagment.Exception.BadRequestException;
import com.project.schoolmanagment.entity.concretes.LessonProgram;
import com.project.schoolmanagment.utils.Messages;

import java.util.HashSet;
import java.util.Set;

public class CheckSameLessonProgram {

    public static void checkLessonPrograms(Set<LessonProgram> existLessonProgram, Set<LessonProgram> lessonProgramRequest) {
        if (existLessonProgram.size() == 0 && lessonProgramRequest.size() > 1) {
            for (LessonProgram lessonProgramCopy : new HashSet<>(lessonProgramRequest)) {
                for (LessonProgram requestLessonProgramOrigin : lessonProgramRequest) {
                    if (lessonProgramCopy.getStartTime().equals(requestLessonProgramOrigin.getStartTime()) &&
                            lessonProgramCopy.getDay().name().equals(requestLessonProgramOrigin.getDay().name())) {
                        throw new BadRequestException(Messages.LESSON_PROGRAM_EXIST_MESSAGE);
                    }
                }
            }
        } else {
            for (LessonProgram lessonProgram : existLessonProgram) {
                for (LessonProgram requestLessonProgram : lessonProgramRequest) {
                    if (lessonProgram.getStartTime().equals(requestLessonProgram.getStartTime()) &&
                            lessonProgram.getDay().name().equals(requestLessonProgram.getDay().name())) {
                        throw new BadRequestException(Messages.LESSON_PROGRAM_EXIST_MESSAGE);
                    }
                }
            }
        }
    }
}
