package com.project.schoolmanagment.utils;

import com.project.schoolmanagment.entity.concretes.LessonProgram;
import com.project.schoolmanagment.exception.BadRequestException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
@Component
public class CheckSameLessonProgram {

	//TODO we have to validate also old lesson program and new lesson program time schedule matching.

	// 09:00 - 12:00
	// 11:00 - 13:00


	public void checkLessonPrgrams(Set<LessonProgram> existLessonProgram, Set<LessonProgram> lessonProgramRequest){
		if(existLessonProgram.isEmpty() && lessonProgramRequest.size()>1){
			checkDuplicateLessonPrograms(lessonProgramRequest);
		} else {
			checkDuplicateLessonPrograms(lessonProgramRequest);
			checkDuplicateLessonPrograms(existLessonProgram,lessonProgramRequest);
		}
	}

	private void checkDuplicateLessonPrograms(Set<LessonProgram> lessonPrograms){
		Set<String>uniqueLessonProgramKeys = new HashSet<>();

		for (LessonProgram lessonProgram : lessonPrograms){
			String lessonProgramKey = lessonProgram.getDay().name() + lessonProgram.getStartTime();
			if(uniqueLessonProgramKeys.contains(lessonProgramKey)){
				throw new BadRequestException(Messages.LESSON_PROGRAM_EXIST_MESSAGE);
			}
			uniqueLessonProgramKeys.add(lessonProgramKey);
		}
	}

	private void checkDuplicateLessonPrograms (Set<LessonProgram> existLessonProgram, Set<LessonProgram> lessonProgramRequest){
		for(LessonProgram requestLessonProgram : lessonProgramRequest){
			if (existLessonProgram.stream().anyMatch(lessonProgram ->
					lessonProgram.getStartTime().equals(requestLessonProgram.getStartTime() )
					&& 	lessonProgram.getDay().name().equals(requestLessonProgram.getDay().name()))){
				throw new BadRequestException(Messages.LESSON_PROGRAM_EXIST_MESSAGE);
			}
		}
	}

}
