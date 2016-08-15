package com.company.conference;

/**
 * This class stores all attributes of a Talk
 * 
 * @author Gaurav
 *
 */
public class Talk implements Comparable {
	private String name;
	private int duration;
	private String scheduledTime;
	private SessionSlot sessionSlot;
	private boolean isScheduled;

	public Talk(String name, int duration) {
		this.name = name;
		this.duration = duration;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(String scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	public SessionSlot getSessionSlot() {
		return sessionSlot;
	}

	public void setSessionSlot(SessionSlot sessionSlot) {
		this.sessionSlot = sessionSlot;
	}

	public boolean isScheduled() {
		return isScheduled;
	}

	public void setScheduled(boolean isScheduled) {
		this.isScheduled = isScheduled;
	}

	// In case sorting is needed
	@Override
	public int compareTo(Object obj) {
		Talk talk = (Talk) obj;
		if (this.duration > talk.duration)
			return -1;
		else if (this.duration < talk.duration)
			return 1;
		else
			return 0;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (!(o instanceof Talk)) {
			return false;
		}

		Talk t = (Talk) o;
		if (t.getDuration() == this.getDuration() && t.getName().equals(this.getName())) {
			return true;
		}
		return false;
	}

}
