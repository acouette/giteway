package org.kwet.giteway.github.impl;

import java.util.List;

import org.kwet.giteway.github.AbstractGitConnector;
import org.kwet.giteway.github.GitSearchConnector;
import org.kwet.giteway.model.Repositories;
import org.kwet.giteway.model.RepositorySearch;
import org.springframework.stereotype.Component;

@Component
public class GitSearchConnectorImpl extends AbstractGitConnector implements GitSearchConnector {

	static final String GET_REPOSITORIES_BY_KEYWORD = buildUrl("/legacy/repos/search/{keyword}");

	@Override
	public List<RepositorySearch> searchRepositoryByKeyword(String keyword) {
		return gitHttpClient.executeRequest(GET_REPOSITORIES_BY_KEYWORD, Repositories.class, keyword).getRepositories();
	}

}
