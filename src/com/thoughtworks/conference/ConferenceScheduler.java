package com.thoughtworks.conference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class holds the logic to schedule the conference.
 * 
 * @author Gaurav
 *
 */
public class ConferenceScheduler {

	private ArrayList<Talk> talkList;

	public ConferenceScheduler(ArrayList<Talk> talkList) {
		this.talkList = talkList;
	}

	public Conference prepareScheduleForConference() throws ScheduleException {
		return getConferenceSchedule(talkList);
	}

	/**
	 * a. Find No of days for Conference 
	 * b. Take talk list into temp talk list needed for manipulation 
	 * c. Make all the morning sessions since they have a strict constraint 
	 * d. remove all the tasks of morning sessions from temp talk list 
	 * e. Repeat c,d for Afternoon sessions 
	 * d. If any talks are left, try to fit in Afternoon Sessions. 
	 * e. If still talks are left then conference cannot be scheduled without gaps
	 * f. Send the sessions for scheduling by calling  getScheduledTracks()
	 * g. Store the tracklist in the Conference and return the same
	 * 
	 * @param talkList
	 * @return Conference
	 * @throws ScheduleException
	 */
	private Conference getConferenceSchedule(ArrayList<Talk> talkList) throws ScheduleException {

		ArrayList<ArrayList<Talk>> morningSessions = new ArrayList<ArrayList<Talk>>();
		ArrayList<ArrayList<Talk>> afternoonSessions = new ArrayList<ArrayList<Talk>>();
		ArrayList<Track> trackList = new ArrayList<Track>();
		Conference conference = new Conference();
		int minDayTime = Constants.MIN_DAY_TIME;
		int totalTalkListTime = getTotalTimeForTalkList(talkList);
		int totalNoOfDays = totalTalkListTime / minDayTime;

		List<Talk> tempTalkList = new ArrayList<Talk>();
		tempTalkList.addAll(talkList);

		for (int i = 0; i < totalNoOfDays; i++) {
			morningSessions.add(makeMorningSession(tempTalkList));
			tempTalkList.removeAll(morningSessions.get(i));

		}
		for (int i = 0; i < totalNoOfDays; i++) {
			afternoonSessions.add(makeAfternoonSession(tempTalkList));
			tempTalkList.removeAll(afternoonSessions.get(i));
		}

		// recheck if task is left - place it in Afternoon session 
		int maxSessionTimeLimit = Constants.MAX_TIME_AFTERNOON_SESSION;
		if (!tempTalkList.isEmpty()) {
			List<Talk> scheduledTalkList = new ArrayList<Talk>();
			for (List<Talk> afternoonSessionList : afternoonSessions) {
				int totalTime = getTotalTimeForTalkList(afternoonSessionList);

				for (Talk talk : tempTalkList) {
					int talkTime = talk.getDuration();

					if (talkTime + totalTime <= maxSessionTimeLimit) {
						afternoonSessionList.add(talk);
						//talk.setScheduled(true);
						scheduledTalkList.add(talk);
					}
				}

				tempTalkList.removeAll(scheduledTalkList);
				if (tempTalkList.isEmpty())
					break;
			}
		}
		// No talks should be present in the list if scheduling is perfect
		if (!tempTalkList.isEmpty()) {
			throw new ScheduleException(Constants.CANNOT_SCHEDULE);
		}

		trackList = getScheduledTracks(morningSessions, afternoonSessions);
		conference.setTrackList(trackList);
		return conference;

	}

	/**
	 * 
	 * 	a. Initialize date
	 *  b. Schedule Morning Session
	 *  c. Schedule Lunch
	 *  d. Schedule Afternoon Session
	 *  e. Schedule Evening Session
	 *  f. repeat for all days
	 *  g. Set the sessions in Track and return the Track list
	 * @param morningSessions
	 * @param afternoonSessions
	 * @return
	 */
	private ArrayList<Track> getScheduledTracks(ArrayList<ArrayList<Talk>> morningSessions,
			ArrayList<ArrayList<Talk>> afternoonSessions) {
		Session morningSession = null;
		Session afternoonSession = null;
		Track track = null;
		ArrayList<Track> tracks = new ArrayList<Track>();
		int lunchTimeDuration = Constants.LUNCH_TIME;
		Talk networkingTalk = new Talk(Constants.NETWORKING_EVENT, Constants.NETWORKING_SESSION_TIME);
		Talk lunchTalk = new Talk(Constants.LUNCH, Constants.LUNCH_TIME);
		int totalNoOfDays = morningSessions.size();

		// schedule event for all days.
		for (int dayCount = 0; dayCount < totalNoOfDays; dayCount++) {
			List<Talk> talkList = new ArrayList<Talk>();

			// Create a date and initialize start time 09:00 AM.
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.TIME_FORMAT);
			date.setHours(9);
			date.setMinutes(0);
			date.setSeconds(0);

			String scheduledTime = dateFormat.format(date);

			// Schedule Morning Session 
			List<Talk> mornSessionTalkList = morningSessions.get(dayCount);
			for (Talk talk : mornSessionTalkList) {
				talk.setScheduled(true);
				talk.setSessionSlot(SessionSlot.MORNING);
				talk.setScheduledTime(scheduledTime);
				scheduledTime = Util.getNextEventTime(date, talk.getDuration());
				talkList.add(talk);
			}

			// Schedule Lunch Time 
			lunchTalk.setScheduledTime(scheduledTime);
			mornSessionTalkList.add(lunchTalk);
			talkList.add(lunchTalk);

			// Schedule Afternoon Session
			scheduledTime = Util.getNextEventTime(date, lunchTimeDuration);
			List<Talk> afternoonSessionTalkList = afternoonSessions.get(dayCount);
			for (Talk talk : afternoonSessionTalkList) {
				talk.setScheduled(true);
				talk.setSessionSlot(SessionSlot.AFTERNOON);
				talk.setScheduledTime(scheduledTime);
				talkList.add(talk);
				scheduledTime = Util.getNextEventTime(date, talk.getDuration());
			}

			// Schedule Networking Event at the end of track.
			networkingTalk.setScheduledTime(scheduledTime);
			talkList.add(networkingTalk);
			afternoonSessionTalkList.add(networkingTalk);

		}

		//Set the Tracks and its sessions
		for (int i = 0; i < totalNoOfDays; i++) {
			morningSession = new Session();
			afternoonSession = new Session();
			morningSession.setTalks(morningSessions.get(i));
			afternoonSession.setTalks(afternoonSessions.get(i));

			track = new Track();
			track.setMorningSession(morningSession);
			track.setAfternoonSession(afternoonSession);

			tracks.add(track);
		}

		return tracks;
	}

	/**
	 * This make a list of talks for a morning session Morning Session has only
	 * one constraint time limit = 180 mins
	 * 
	 * It adds a talk to the schedule if the session time after adding remains
	 * less than or equal to MAX_TIME_MORNING_SESSION
	 * 
	 * @param talkList
	 * @return ArrayList<Talk>
	 * @throws ScheduleException
	 */
	private ArrayList<Talk> makeMorningSession(List<Talk> talkList) throws ScheduleException {
		int maxSessionTimeLimit = Constants.TIME_LIMIT_MORNING_SESSION;
		int totalTime = 0;
		int talkTime = 0;
		int startPoint = 0;
		int currentCount = 0;
		int talkListSize = talkList.size();
		Talk currentTalk = null;
		ArrayList<Talk> morningSessionTalk = null;
		;

		for (int count = 0; count < talkListSize; count++) {
			startPoint = count;
			totalTime = 0;
			morningSessionTalk = new ArrayList<Talk>();

			while (startPoint != talkListSize) {

				currentCount = startPoint;
				startPoint++;
				currentTalk = talkList.get(currentCount);
				
				talkTime = currentTalk.getDuration();

				if (talkTime > maxSessionTimeLimit || talkTime + totalTime > maxSessionTimeLimit) {
					continue;
				}
				morningSessionTalk.add(currentTalk);
				totalTime += talkTime;
				// if constraint satisfied
				if (totalTime == maxSessionTimeLimit)
					break;

			}

			if (totalTime == maxSessionTimeLimit)
				break;
		}

		if (totalTime != maxSessionTimeLimit)
			throw new ScheduleException(Constants.CANNOT_SCHEDULE);

		return morningSessionTalk;

	}

	/**
	 * This make a list of talks for an afternoon session Afternoon Session has
	 * two constraint time limit = 180 - 240 mins
	 * 
	 * It adds a talk to the schedule if the session time after adding remains
	 * less than or equal to MIN_TIME_AFTERNOON_SESSION
	 * 
	 * @param talkList
	 * @return ArrayList<Talk>
	 * @throws ScheduleException
	 */
	private ArrayList<Talk> makeAfternoonSession(List<Talk> talkList) throws ScheduleException {
		int minSessionTimeLimit = Constants.MIN_TIME_AFTERNOON_SESSION;
		int maxSessionTimeLimit = Constants.MAX_TIME_AFTERNOON_SESSION;
		int totalTime = 0;
		int talkTime = 0;
		int startPoint = 0;
		int currentCount = 0;
		int talkListSize = talkList.size();
		Talk currentTalk = null;
		ArrayList<Talk> afternoonSessionTalk = null;

		for (int count = 0; count < talkListSize; count++) {
			afternoonSessionTalk = new ArrayList<Talk>();
			startPoint = count;
			totalTime = 0;

			while (startPoint != talkListSize) {
				currentCount = startPoint;
				startPoint++;
				currentTalk = talkList.get(currentCount);
				
				talkTime = currentTalk.getDuration();

				if (talkTime > maxSessionTimeLimit || talkTime + totalTime > maxSessionTimeLimit) {
					continue;
				}
				afternoonSessionTalk.add(currentTalk);
				totalTime += talkTime;
				// if constraint satisfied
				if (totalTime >= minSessionTimeLimit)
					break;

			}

			if (totalTime >= minSessionTimeLimit)
				break;
		}

		if (totalTime <= minSessionTimeLimit)
			throw new ScheduleException(Constants.CANNOT_SCHEDULE);

		return afternoonSessionTalk;

	}
	

	/**
	 * Gets the Total time for the list
	 * @param talkList
	 * @return
	 */
	private int getTotalTimeForTalkList(List<Talk> talkList) {
		if (talkList == null || talkList.isEmpty())
			return 0;

		int totalTime = 0;
		for (Talk talk : talkList) {
			totalTime += talk.getDuration();
		}
		return totalTime;
	}

}
