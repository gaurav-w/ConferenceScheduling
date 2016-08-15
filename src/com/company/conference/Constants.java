package com.company.conference;

/**
 * This class will have the contants used in the package. It will also store all
 * error or other messages displayed to the user.
 * 
 * @author Gaurav
 *
 */
public class Constants {

	// File constants
	public static final String FILE_PATH = "C:\\Users\\Gaurav\\Desktop\\";
	public static final String FILE_NAME = "input.txt";

	// Line Terminators
	public static final String MIN = "min";
	public static final String LIGHTNING = "lightning";

	public static final int TIME_LIMIT_MORNING_SESSION = 180;
	public static final int MIN_TIME_AFTERNOON_SESSION = 180;
	public static final int MAX_TIME_AFTERNOON_SESSION = 240;
	public static final int MIN_DAY_TIME = 6 * 60;
	public static final int LUNCH_TIME = 60;
	public static final int NETWORKING_SESSION_TIME = 60; // assumption

	public static final String NETWORKING_EVENT = "Networking Event";
	public static final String LUNCH = "Lunch";
	public static final String TIME_FORMAT = "hh:mma ";
	public static final String TRACK = "Track ";
	// error messages
	public static final String FILE_NOT_FOUND = "File not found on the given path : ";
	public static final String EMPTY_FILE = "There are no contents in the input file : ";
	public static final String TALK_ERROR = "Error on talk: ";
	public static final String MISSING_NAME_DURATION = "Talk name or duration is not mentioned.";
	public static final String IMPROPER_DURATION = "Improper Talk duration. Duration should be in min or in lightning.";
	public static final String DURATION_CONSTRAINT = "Duration of the Talk should be restricted to (0-240) minutes";
	public static final String TALK_NAME_BLANK = "Talk name is blank";
	public static final String PERIOD = ".";
	public static final String CANNOT_SCHEDULE = "Scheduling Failed : There are gaps in the schedule";

}
