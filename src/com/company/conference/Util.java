package com.company.conference;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class is reserved for utility functions needed for the package.
 * 
 * @author Gaurav
 *
 */
public class Util {

	public static void printMessage(String msg) {
		System.out.println(msg);
	}

	public static String getNextEventTime(Date date, int timeDuration) {
		long timeInLong = date.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mma ");

		long timeDurationInLong = timeDuration * 60 * 1000;
		long newTimeInLong = timeInLong + timeDurationInLong;

		date.setTime(newTimeInLong);
		String timeString = dateFormat.format(date);
		return timeString;
	}
}
