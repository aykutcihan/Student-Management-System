package com.project.schoolmanagment.utils;

public class Messages {

	private Messages() {
	}

	public static final String NOT_PERMITTED_METHOD_MESSAGE = "You do not have any permission to do this operation";
	public static final String NOT_FOUND_USER_MESSAGE = "Error: User not found with id %s";

	public static final String ROLE_NOT_FOUND = "There is no role like that, check the database";
	public static final String ROLE_ALREADY_EXIST = "Role already exist in DB";

	public static final String ALREADY_REGISTER_MESSAGE_USERNAME = "Error: User with username %s is already registered";
	public static final String ALREADY_REGISTER_MESSAGE_SSN = "Error: User with ssn %s is already registered";
	public static final String ALREADY_REGISTER_MESSAGE_PHONE_NUMBER = "Error: User with phone number %s is already registered";
	public static final String ALREADY_REGISTER_MESSAGE_EMAIL = "Error: User with email %s is already registered";
	//CRTL+SHIFT+U
	public static final String ALREADY_SEND_A_MESSAGE_TODAY = "Error: You have already send a message with this e-mail";

	//education term
	public static final String EDUCATION_START_DATE_IS_EARLIER_THAN_LAST_REGISTRATION_DATE = "Error: The start date cannot be earlier than the last registration date " ;
	public static final String EDUCATION_END_DATE_IS_EARLIER_THAN_START_DATE = "Error: The end date cannot be earlier than the start date " ;
	public static final String EDUCATION_TERM_IS_ALREADY_EXIST_BY_TERM_AND_YEAR_MESSAGE = "Error: Education Term with Term And Year already exist " ;
	public static final String EDUCATION_TERM_NOT_FOUND_MESSAGE = "Error: Education Term with id %s not found" ;

	//lesson
	public static final String ALREADY_REGISTER_LESSON_MESSAGE = "Error: Lesson with lesson name %s already registered" ;
	public static final String NOT_FOUND_LESSON_MESSAGE = "Error: Lesson with this field %s not found";

	public static final String NOT_FOUND_LESSON_IN_LIST = "Error: Lesson not found in the list" ;
	public static final String TIME_NOT_VALID_MESSAGE = "Error: incorrect time" ;

	//lesson program
	public static final String NOT_FOUND_LESSON_PROGRAM_MESSAGE = "Error: Lesson program with this field %s not found";
	public static final String NOT_FOUND_LESSON_PROGRAM_MESSAGE_WITHOUT_ID_INFO = "Error: Lesson program with this field not found";
	public static final String LESSON_PROGRAM_EXIST_MESSAGE = "Error: Course schedule can not be selected for the same hour and date" ;

	//advisor teacher
	public static final String NOT_FOUND_ADVISOR_MESSAGE = "Error: Advisor Teacher with id %s not found" ;
	public static final String NOT_FOUND_ADVISOR_MESSAGE_WITH_USERNAME = "Error: Advisor Teacher with username %s not found" ;

	//student
	public static final String STUDENT_INFO_NOT_FOUND = "Error: Student Info with id %d not found" ;
	public static final String STUDENT_INFO_NOT_FOUND_BY_STUDENT_ID= "Error: Student Info with  student id %d not found" ;

	//meet
	public static final String MEET_NOT_FOUND_MESSAGE = "Error: Meet with id %d not found" ;

}
