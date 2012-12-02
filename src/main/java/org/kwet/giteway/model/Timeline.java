package org.kwet.giteway.model;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Timeline implements Serializable{

	private List<TimelineInterval> timelineIntervals;

	private double timelineDays;

	private double intervalDays;
	
	private int commitCount;

	public List<TimelineInterval> getTimelineIntervals() {
		return timelineIntervals;
	}

	public void setTimelineIntervals(List<TimelineInterval> timelineIntervals) {
		this.timelineIntervals = timelineIntervals;
	}

	public Timeline(List<TimelineInterval> timelineIntervals) {
		this.timelineIntervals = timelineIntervals;
	}

	public double getTimelineDays() {
		return timelineDays;
	}

	public void setTimelineDays(double timelineDays) {
		this.timelineDays = timelineDays;
	}

	public double getIntervalDays() {
		return intervalDays;
	}

	public void setIntervalDays(double intervalDays) {
		this.intervalDays = intervalDays;
	}
	

	public int getCommitCount() {
		return commitCount;
	}

	public void setCommitCount(int commitCount) {
		this.commitCount = commitCount;
	}

	public Timeline() {
	}

}
