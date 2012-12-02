package org.kwet.giteway.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TimelineInterval implements Serializable {

	private long start;

	private long end;

	private int commitCount;

	public TimelineInterval() {
	}

	public TimelineInterval(long start, long end, int commitCount) {
		super();
		this.start = start;
		this.end = end;
		this.commitCount = commitCount;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	public int getCommitCount() {
		return commitCount;
	}

	public void setCommitCount(int commitCount) {
		this.commitCount = commitCount;
	}

	@Override
	public String toString() {
		return "TimelineInterval [start=" + start + ", end=" + end + ", commitCount=" + commitCount + "]";
	}
	
	

}
