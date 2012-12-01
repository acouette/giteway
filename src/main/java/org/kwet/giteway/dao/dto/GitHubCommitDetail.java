package org.kwet.giteway.dao.dto;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author a.couette
 *
 */
@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubCommitDetail implements Serializable {

	private String message;

	private GitHubCommitCommitter committer;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public GitHubCommitCommitter getCommitter() {
		return committer;
	}

	public void setCommitter(GitHubCommitCommitter committer) {
		this.committer = committer;
	}

	
	

}