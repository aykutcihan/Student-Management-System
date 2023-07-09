package com.project.schoolmanagment.utils;

import com.project.schoolmanagment.exception.BadRequestException;

import java.time.LocalTime;

public class TimeControl {

	//start time should be earlier than stop time
	public static boolean checkTime(LocalTime start, LocalTime stop){
		return start.isAfter(stop) || start.equals(stop);
	}

	public static void checkTimeWithException(LocalTime start,LocalTime stop){
		if (checkTime(start,stop)) {
			throw new BadRequestException(Messages.TIME_NOT_VALID_MESSAGE);
		}
	}
}
