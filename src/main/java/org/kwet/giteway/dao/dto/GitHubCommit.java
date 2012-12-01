package org.kwet.giteway.dao.dto;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author Antoine Couette
 *
 */
@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubCommit implements Serializable {

	private GitHubCommitDetail commit;
	
	private GitHubUser committer;

	public GitHubCommitDetail getCommit() {
		return commit;
	}

	public void setCommit(GitHubCommitDetail commit) {
		this.commit = commit;
	}

	public GitHubUser getCommitter() {
		return committer;
	}

	public void setCommitter(GitHubUser committer) {
		this.committer = committer;
	}

}
