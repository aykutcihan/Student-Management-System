package com.project.schoolmanagment.service.util;

import com.project.schoolmanagment.Exception.BadRequestException;
import com.project.schoolmanagment.entity.concretes.LessonProgram;
import com.project.schoolmanagment.entity.concretes.Meet;
import com.project.schoolmanagment.utils.Messages;

import java.util.HashSet;
import java.util.Set;

public class CheckSameMeet {

    public static void checkLessonPrograms(Set<Meet> existMeet, Set<Meet> meetRequest) {
        if (existMeet.size() == 0 && existMeet.size() > 1) {
            for (Meet meetCopy : new HashSet<>(meetRequest)) {
                for (Meet meetRequestOrigin : meetRequest) {
                    if (meetCopy.getStartTime().equals(meetRequestOrigin.getStartTime()) &&
                            meetCopy.getDate().equals(meetRequestOrigin.getDate())) {
                        throw new BadRequestException(Messages.LESSON_PROGRAM_EXIST_MESSAGE);
                    }
                }
            }
        } else {
            for (Meet meet : existMeet) {
                for (Meet requestMeet: meetRequest) {
                    if (meet.getStartTime().equals(requestMeet.getStartTime()) &&
                            meet.getDate().equals(requestMeet.getDate())) {
                        throw new BadRequestException(Messages.LESSON_PROGRAM_EXIST_MESSAGE);
                    }
                }
            }
        }
    }
}
