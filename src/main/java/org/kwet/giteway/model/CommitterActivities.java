package org.kwet.giteway.model;

import java.util.List;

public class CommitterActivities {

	private List<CommitterActivity> committerActivityList;

	private int commitCount;

	public List<CommitterActivity> getCommitterActivityList() {
		return committerActivityList;
	}

	public void setCommitterActivityList(List<CommitterActivity> committerActivityList) {
		this.committerActivityList = committerActivityList;
	}

	public int getCommitCount() {
		return commitCount;
	}

	public void setCommitCount(int commitCount) {
		this.commitCount = commitCount;
	}

	public CommitterActivities() {
	}

}
