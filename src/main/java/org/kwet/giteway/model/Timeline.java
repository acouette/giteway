package org.kwet.giteway.model;

import java.util.List;

public class Timeline {

	private List<TimelineChunk> timelineChunks;

	public List<TimelineChunk> getTimelineChunks() {
		return timelineChunks;
	}

	public void setTimelineChunks(List<TimelineChunk> timelineChunks) {
		this.timelineChunks = timelineChunks;
	}

	public Timeline(List<TimelineChunk> timelineChunks) {
		this.timelineChunks = timelineChunks;
	}

	public Timeline() {
	}

}
