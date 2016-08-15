package com.company.conference;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * This is the Main class to run the project
 * a. Parse the input file - the path is stored in Constants file
 * b. ConferenceScheduler must be iniliazed with Talk list from the parser. 
 * c. It will return Conference object which has display functionality.
 * 
 * @author Gaurav
 *
 */
public class ConferencePlanner {

	public static void main(String[] args) throws TalkException, ScheduleException, FileNotFoundException {

		InputFileParser parser = new InputFileParser(Constants.FILE_PATH + Constants.FILE_NAME);
		ArrayList<String> lines = parser.getFileContentLines();

		ConferenceScheduler cs = new ConferenceScheduler(parser.getTalkList(lines));

		Conference conference = cs.prepareScheduleForConference();
		conference.displayConference();

	}
}
