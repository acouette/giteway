package org.kwet.giteway.model;

import java.util.List;

public class CommitterActivities {

	private List<CommitterActivity> committerActivities;

	public List<CommitterActivity> getCommitterActivities() {
		return committerActivities;
	}

	public void setCommitterActivities(List<CommitterActivity> committerActivities) {
		this.committerActivities = committerActivities;
	}

	public CommitterActivities(List<CommitterActivity> committerActivities) {
		this.committerActivities = committerActivities;
	}

	public CommitterActivities() {
	}

}
