package org.kwet.giteway.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.kwet.giteway.dao.GitSearchConnector;
import org.kwet.giteway.dao.dto.DtoToModel;
import org.kwet.giteway.dao.dto.GitHubRepositories;
import org.kwet.giteway.dao.dto.GitHubRepositorySearch;
import org.kwet.giteway.model.Repository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * The Class GitSearchConnectorImpl.
 * 
 * @author a.couette
 * 
 */
@Component
public class GitSearchConnectorImpl extends AbstractGitConnector implements GitSearchConnector {

	private static final String GET_REPOSITORIES_BY_KEYWORD = buildUrl("/legacy/repos/search/{keyword}");

	@Override
	@Cacheable("repositories")
	public List<Repository> searchRepositoryByKeyword(String keyword) {
		Validate.notEmpty(keyword, "Keyword must be null and not empty");
		GitHubRepositories gitHubRepositories = getGitHttpClient().executeGetRequest(GET_REPOSITORIES_BY_KEYWORD, GitHubRepositories.class, keyword);
		List<Repository> repositories = new ArrayList<>();
		for (GitHubRepositorySearch gr : gitHubRepositories.getRepositories()) {
			Repository repository = DtoToModel.getRepository(gr);
			repositories.add(repository);
		}
		return repositories;

	}

	@Override
	@Cacheable("autosuggests")
	public List<String> searchRepositoryNames(String keyword, int limit) {
		Validate.notEmpty(keyword, "Keyword must be null and not empty");
		GitHubRepositories gitHubRepositories = getGitHttpClient().executeGetRequest(GET_REPOSITORIES_BY_KEYWORD, GitHubRepositories.class, keyword);
		List<String> repositoryNames = new ArrayList<>();
		int i = 0;
		for (GitHubRepositorySearch gr : gitHubRepositories.getRepositories()) {
			if(i==limit){
				break;
			}
			if(gr.getName().startsWith(keyword) && !repositoryNames.contains(gr.getName())){
				repositoryNames.add(gr.getName());
				i++;
			}
		}
		Collections.sort(repositoryNames);
		return repositoryNames;
	}

}
