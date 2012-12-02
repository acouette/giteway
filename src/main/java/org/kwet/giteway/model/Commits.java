package org.kwet.giteway.model;

import java.util.List;

/**
 * @author a.couette
 * 
 */
public class Commits {

	private String repositoryOwner;

	private String repositoryName;

	private List<Commit> commitList;

	public Commits() {
	}

	public Commits(String repositoryOwner, String repositoryName) {
		this.repositoryName = repositoryName;
		this.repositoryOwner = repositoryOwner;
	}

	public String getRepositoryName() {
		return repositoryName;
	}

	public void setRepositoryName(String repositoryName) {
		this.repositoryName = repositoryName;
	}

	public String getRepositoryOwner() {
		return repositoryOwner;
	}

	public void setRepositoryOwner(String repositoryOwner) {
		this.repositoryOwner = repositoryOwner;
	}

	public List<Commit> getCommitList() {
		return commitList;
	}

	public void setCommitList(List<Commit> commitList) {
		this.commitList = commitList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((repositoryName == null) ? 0 : repositoryName.hashCode());
		result = prime * result + ((repositoryOwner == null) ? 0 : repositoryOwner.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Commits other = (Commits) obj;
		if (repositoryName == null) {
			if (other.repositoryName != null) {
				return false;
			}
		} else if (!repositoryName.equals(other.repositoryName)) {
			return false;
		}
		if (repositoryOwner == null) {
			if (other.repositoryOwner != null) {
				return false;
			}
		} else if (!repositoryOwner.equals(other.repositoryOwner)) {
			return false;
		}
		return true;
	}

}
