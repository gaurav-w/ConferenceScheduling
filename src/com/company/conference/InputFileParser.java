package com.company.conference;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class has all the functions needed to read the input file and convert
 * them into Talk objects.
 * 
 * @author Gaurav
 *
 */
public class InputFileParser {

	private String filePath;

	public InputFileParser(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * The function reads the file line by line and returns them in arraylist of
	 * String
	 * 
	 * @param filePath
	 * @return ArrayList<String>
	 * @throws FileNotFoundException 
	 */
	public ArrayList<String> getFileContentLines() throws FileNotFoundException {
		ArrayList<String> lineList = new ArrayList<String>();

		try {
			FileReader inputFile = new FileReader(filePath);
			BufferedReader bufferedReader = new BufferedReader(inputFile);
			String strLine = bufferedReader.readLine();

			while (strLine != null) {
				lineList.add(strLine);
				strLine = bufferedReader.readLine();
			}
			bufferedReader.close();

		} catch (FileNotFoundException e) {
			Util.printMessage(Constants.FILE_NOT_FOUND + getFilePath());
			throw new FileNotFoundException();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lineList;
	}

	/**
	 * This functions takes an arraylist of String
	 * 	a. Iterates list and Extracts the name and duration of each talk
	 *  b. Validates each talk for constraints
	 *  c. Returns arraylist of Talk
	 *  
	 * @param lineList
	 * @return ArrayList<Talk>
	 * @throws TalkException
	 */
	public ArrayList<Talk> getTalkList(ArrayList<String> lineList) throws TalkException {

		ArrayList<Talk> talkList = new ArrayList<Talk>();
		int duration = 0;
		String name = null;
		String durationStr = null;
		Talk talk = null;
		
		// If empty file is given as input
		if (lineList == null || lineList.isEmpty())
			throw new TalkException(Constants.EMPTY_FILE + getFilePath());

		for (String line : lineList) {
			int lastSpaceIndex = line.lastIndexOf(" ");
			// line with no space
			if (lastSpaceIndex == -1)
				throw new TalkException(
						Constants.TALK_ERROR + line + Constants.PERIOD + Constants.MISSING_NAME_DURATION);

			name = line.substring(0, lastSpaceIndex);
			durationStr = line.substring(lastSpaceIndex + 1);

			validateTalk(line, name, durationStr);

			if (durationStr.endsWith(Constants.MIN)) {
				duration = Integer.parseInt(durationStr.substring(0, durationStr.indexOf(Constants.MIN)));
			} else if (durationStr.endsWith(Constants.LIGHTNING)) {
				duration = 5;
			}
			talk = new Talk(name, duration);
			
			talkList.add(talk);
		}
		return talkList;
	}

	/**
	 * This function validates talk name and duration
	 * a. talk name missing
	 * b. duration is not terminated with min or lightning.
	 * c. duration not in 0-240 range
	 * d. non numeric duration
	 * 
	 * @param talk
	 * @param name
	 * @param durationStr
	 * @throws TalkException
	 */
	private void validateTalk(String talk, String name, String durationStr) throws TalkException {
		int duration = 0;

		if (name == null || "".equals(name.trim()))
			throw new TalkException(Constants.TALK_ERROR + talk + Constants.PERIOD + Constants.TALK_NAME_BLANK);

		if (!durationStr.endsWith(Constants.MIN) && !durationStr.endsWith(Constants.LIGHTNING))
			throw new TalkException(Constants.TALK_ERROR + talk + Constants.PERIOD + Constants.IMPROPER_DURATION);

		if (durationStr.endsWith(Constants.MIN)) {
			try {
				duration = Integer.parseInt(durationStr.substring(0, durationStr.indexOf(Constants.MIN)));

				if (duration < 0 || duration > 240) {
					throw new TalkException(
							Constants.TALK_ERROR + talk + Constants.PERIOD + Constants.DURATION_CONSTRAINT);
				}
			} catch (NumberFormatException e) {
				throw new TalkException(Constants.TALK_ERROR + talk + Constants.PERIOD + Constants.IMPROPER_DURATION);
			}
		}
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filename) {
		this.filePath = filename;
	}

}
