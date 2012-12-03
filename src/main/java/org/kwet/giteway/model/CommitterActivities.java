package org.kwet.giteway.model;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class CommitterActivities implements Serializable{
	
	private Repository repository;

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

	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}
	
	

}
