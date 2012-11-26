package org.kwet.giteway.github.impl;

import java.io.InputStream;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.kwet.giteway.github.AbstractGitConnector;
import org.kwet.giteway.github.GitSearchConnector;
import org.kwet.giteway.model.Repositories;
import org.kwet.giteway.model.RepositorySearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GitSearchConnectorImpl extends AbstractGitConnector implements GitSearchConnector {

	
	@Autowired
	private HttpClient httpClient;

	static final String GET_REPOSITORIES_BY_KEYWORD = BASE_GITHUB_URL + "/legacy/repos/search/{keyword}";

	@Override
	public List<RepositorySearch> searchRepositoryByKeyword(String keyword) {
		InputStream is = gitConnector.executeRequest(GET_REPOSITORIES_BY_KEYWORD.replace("{keyword}", keyword));
		return gitConnector.readValue(is, Repositories.class).getRepositories();
	}

}
