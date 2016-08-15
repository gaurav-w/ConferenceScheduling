package com.company.conference;

import java.util.ArrayList;

/**
 * Conference is composition of Tracks
 * 
 * @author Gaurav
 *
 */
public class Conference {

	private ArrayList<Track> trackList;

	public ArrayList<Track> getTrackList() {
		return trackList;
	}

	public void setTrackList(ArrayList<Track> trackList) {
		this.trackList = trackList;
	}

	public void displayConference() {
		Track track = null;
		for (int trackCounter = 0; trackCounter < trackList.size(); trackCounter++) {
			track = trackList.get(trackCounter);
			Util.printMessage(Constants.TRACK + (trackCounter + 1));
			track.displayTrack();
			Util.printMessage("");
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (!(o instanceof Conference)) {
			return false;
		}

		Conference t = (Conference) o;
		if (t.getTrackList().equals(this.getTrackList())) {
			return true;
		}
		return false;
	}
}
