package org.kwet.giteway.data.impl;

import java.util.List;

import org.kwet.giteway.data.AbstractGitConnector;
import org.kwet.giteway.data.GitSearchConnector;
import org.kwet.giteway.model.Repositories;
import org.kwet.giteway.model.RepositorySearch;
import org.springframework.web.client.RestOperations;

public class GitSearchConnectorImpl extends AbstractGitConnector implements GitSearchConnector{

	public GitSearchConnectorImpl(RestOperations restOperations) {
		super(restOperations);
	}

	static final String GET_REPOSITORIES_BY_KEYWORD = BASE_GITHUB_URL+"/legacy/repos/search/{keyword}";
	
	public List<RepositorySearch> searchRepositoryByKeyword(String keyword) {
		return restOperations.getForObject(GET_REPOSITORIES_BY_KEYWORD, Repositories.class, keyword).getRepositories();
	}

}
