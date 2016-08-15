package com.company.conference;

import java.util.ArrayList;

/**
 * Session is composition of Talks
 * 
 * @author Gaurav
 *
 */
public class Session {

	private ArrayList<Talk> talks;

	public ArrayList<Talk> getTalks() {
		return talks;
	}

	public void setTalks(ArrayList<Talk> talks) {
		this.talks = talks;
	}

	public void displaySession() {

		for (int i = 0; i < talks.size(); i++) {
			Util.printMessage(talks.get(i).getScheduledTime() + talks.get(i).getName() + " "
					+ (talks.get(i).getDuration() > 5 ? talks.get(i).getDuration() + "min" : "lighting"));
		}

	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (!(o instanceof Session)) {
			return false;
		}

		Session t = (Session) o;
		if (t.getTalks().equals(this.getTalks())) {
			return true;
		}
		return false;
	}
}
