package org.kwet.giteway.model;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class TimelineInterval implements Serializable {

	private Date start;

	private Date end;

	private int commitCount;

	public TimelineInterval() {
	}

	public TimelineInterval(Date start, Date end, int commitCount) {
		super();
		this.start = start;
		this.end = end;
		this.commitCount = commitCount;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
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
