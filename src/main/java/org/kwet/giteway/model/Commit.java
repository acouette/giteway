package org.kwet.giteway.model;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Commit implements Serializable, Comparable<Commit> {

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

	public Date getDate() {
		return commit.getCommitter().getDate();
	}

	public void setDate(Date date) {

		if (commit == null) {
			commit = new CommitDetail();
		}
		if (commit.getCommitter() == null) {
			commit.setCommitter(new Commiter());
		}
		commit.getCommitter().setDate(date);
	}

	@Override
	public int compareTo(Commit o) {
		return this.getDate().compareTo(o.getDate());
	}

}
