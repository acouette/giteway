package org.kwet.giteway.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Commit implements Serializable {

	private User committer;

	private CommitDetail commit;

	public User getCommitter() {
		return committer;
	}

	public void setCommitter(User committer) {
		this.committer = committer;
	}

	public void setCommit(CommitDetail commit) {
		this.commit = commit;
	}

	public String getMessage() {
		if (commit != null) {
			return commit.getMessage();
		}
		return null;
	}

	public void setMessage(String message) {
		if (commit == null) {
			commit = new CommitDetail();
		}
		commit.setMessage(message);
	}

}
