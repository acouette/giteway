package org.kwet.giteway.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CommitterActivity implements Serializable {

	private User committer;

	private int percentage;
	

	public User getCommitter() {
		return committer;
	}

	public void setCommitter(User committer) {
		this.committer = committer;
	}

	public int getPercentage() {
		return percentage;
	}

	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}

	public CommitterActivity() {
	}

	public CommitterActivity(User committer, int percentage) {
		super();
		this.committer = committer;
		this.percentage = percentage;
	}

	@Override
	public String toString() {
		return "CommitterActivity [committer=" + committer + ", percentage=" + percentage + "]";
	}

}
