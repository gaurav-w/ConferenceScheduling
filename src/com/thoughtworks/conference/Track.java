package com.thoughtworks.conference;

/**
 * Track is composition of morning and evening sessions.
 * 
 * @author Gaurav
 *
 */
public class Track {

	private Session morningSession;
	private Session afternoonSession;

	public Session getMorningSession() {
		return morningSession;
	}

	public void setMorningSession(Session morningSession) {
		this.morningSession = morningSession;
	}

	public Session getAfternoonSession() {
		return afternoonSession;
	}

	public void setAfternoonSession(Session afternoonSession) {
		this.afternoonSession = afternoonSession;
	}

	public void displayTrack() {

		morningSession.displaySession();
		afternoonSession.displaySession();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (!(o instanceof Track)) {
			return false;
		}

		Track t = (Track) o;
		if (t.getMorningSession().equals(this.getMorningSession())
				&& t.getAfternoonSession().equals(this.getAfternoonSession())) {
			return true;
		}
		return false;
	}
}
