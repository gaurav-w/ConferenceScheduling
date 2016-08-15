package com.thoughtworks.conference.test;

import org.junit.Test;
import org.junit.Ignore;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.conference.ConferenceScheduler;
import com.thoughtworks.conference.InputFileParser;
import com.thoughtworks.conference.Session;
import com.thoughtworks.conference.Talk;
import com.thoughtworks.conference.TalkException;
import com.thoughtworks.conference.Track;

public class TestConferenceScheduler {

	
	@Test
	public void testTotalTimeInList() throws Exception {

		Talk rails = new Talk("Writing Fast Tests Against Enterprise Rails", 120);
		Talk python = new Talk("Overdoing it in Python", 60);
		ArrayList<Talk> input = new ArrayList<Talk>();
		input.add(rails);
		input.add(python);
		
		ConferenceScheduler cs = new ConferenceScheduler(input);
		
		Method method = cs.getClass().getDeclaredMethod("getTotalTimeForTalkList", List.class);
		method.setAccessible(true);
		Object r = method.invoke(cs, input);

		int mins = (Integer) r;

		boolean testSucess = 180 == mins;

		if (!testSucess) {
			assertFalse(true);
		}
		
	}
		
	@Test
	public void testMorningSessionScheduling() throws Exception {

		Talk rails = new Talk("Writing Fast Tests Against Enterprise Rails", 120);
		Talk python = new Talk("Overdoing it in Python", 60);
		Talk extra = new Talk("Extra", 100);
		ArrayList<Talk> input = new ArrayList<Talk>();
		input.add(rails);
		input.add(python);
		input.add(extra);

		ConferenceScheduler cs = new ConferenceScheduler(input);

		Method method = cs.getClass().getDeclaredMethod("makeMorningSession", List.class);
		method.setAccessible(true);
		Object r = method.invoke(cs, input);
		ArrayList<Talk> output = (ArrayList<Talk>) r;

		Method method2 = cs.getClass().getDeclaredMethod("getTotalTimeForTalkList", List.class);
		method2.setAccessible(true);
		Object r2 = method2.invoke(cs, output);

		int mins = (Integer) r2;

		boolean testSucess = 180 == mins;

		if (!testSucess) {
			assertFalse(true);
		}
	}

	@Test
	public void testAfternoonSessionScheduling() throws Exception {

		Talk rails = new Talk("Writing Fast Tests Against Enterprise Rails", 120);
		Talk python = new Talk("Overdoing it in Python", 160);
		Talk extra = new Talk("Extra", 100);
		ArrayList<Talk> input = new ArrayList<Talk>();
		input.add(rails);
		input.add(python);
		input.add(extra);

		ConferenceScheduler cs = new ConferenceScheduler(input);

		Method method = cs.getClass().getDeclaredMethod("makeAfternoonSession", List.class);
		method.setAccessible(true);
		Object r = method.invoke(cs, input);
		ArrayList<Talk> output = (ArrayList<Talk>) r;

		Method method2 = cs.getClass().getDeclaredMethod("getTotalTimeForTalkList", List.class);
		method2.setAccessible(true);
		Object r2 = method2.invoke(cs, output);

		int mins = (Integer) r2;

		boolean testSucess = (mins >= 180 && mins <= 240);

		if (!testSucess) {
			assertFalse(true);
		}
	}

	@Test
	public void testScheduleTracks() throws Exception {

		Talk rails = new Talk("Writing Fast Tests Against Enterprise Rails", 180);
		Talk python = new Talk("Overdoing it in Python", 240);
		ArrayList<Talk> input = new ArrayList<Talk>();
		input.add(rails);
		input.add(python);

		ArrayList<Talk> morningSession = new ArrayList<Talk>();
		morningSession.add(rails);
		ArrayList<Talk> afternoonSession = new ArrayList<Talk>();
		afternoonSession.add(python);

		ArrayList<ArrayList<Talk>> morningSessions = new ArrayList<ArrayList<Talk>>();
		morningSessions.add(morningSession);

		ArrayList<ArrayList<Talk>> afternoonSessions = new ArrayList<ArrayList<Talk>>();
		afternoonSessions.add(afternoonSession);

		ConferenceScheduler cs = new ConferenceScheduler(input);

		Method method = cs.getClass().getDeclaredMethod("getScheduledTracks", afternoonSessions.getClass(),
				afternoonSessions.getClass());
		method.setAccessible(true);
		Object r = method.invoke(cs, morningSessions, afternoonSessions);
		ArrayList<Track> actual = (ArrayList<Track>) r;
		Track actualTrack = new Track();
		actualTrack = actual.get(0);

		Session mSession = new Session();
		mSession.setTalks(morningSession);
		Session aSession = new Session();
		aSession.setTalks(afternoonSession);

		Track expectedTrack = new Track();

		expectedTrack.setAfternoonSession(aSession);
		expectedTrack.setMorningSession(mSession);

		boolean testSucess = (expectedTrack.equals(actualTrack));

		if (!testSucess) {
			assertFalse(true);
		}
	}

}
