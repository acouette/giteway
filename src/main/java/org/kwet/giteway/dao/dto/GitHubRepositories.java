package org.kwet.giteway.dao.dto;

import java.io.Serializable;
import java.util.List;


@SuppressWarnings("serial")
public class GitHubRepositories implements Serializable{

	private List<GitHubRepositorySearch> repositories;

	public List<GitHubRepositorySearch> getRepositories() {
		return repositories;
	}

	public void setRepositories(List<GitHubRepositorySearch> repositories) {
		this.repositories = repositories;
	}

}