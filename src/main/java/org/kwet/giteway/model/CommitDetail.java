package org.kwet.giteway.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommitDetail implements Serializable {

	private String message;

	private Commiter committer;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Commiter getCommitter() {
		return committer;
	}

	public void setCommitter(Commiter committer) {
		this.committer = committer;
	}

}