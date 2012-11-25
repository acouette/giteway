package org.kwet.giteway.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TimelineData implements Serializable {

	private long timestamp;

	private int commits;

	public TimelineData(long timestamp, int commits) {
		super();
		this.timestamp = timestamp;
		this.commits = commits;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public int getCommits() {
		return commits;
	}

	public void setCommits(int commits) {
		this.commits = commits;
	}

	@Override
	public String toString() {
		return "TimelineData [timestamp=" + timestamp + ", commits=" + commits + "]";
	}

}
