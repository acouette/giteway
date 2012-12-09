package org.kwet.giteway.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.kwet.giteway.dao.GitSearchConnector;
import org.kwet.giteway.dao.dto.DtoToModel;
import org.kwet.giteway.dao.dto.GitHubRepositories;
import org.kwet.giteway.dao.dto.GitHubRepository;
import org.kwet.giteway.dao.dto.GitHubRepositorySearch;
import org.kwet.giteway.dao.dto.GitHubUser;
import org.kwet.giteway.dao.dto.GitHubUsers;
import org.kwet.giteway.model.GitewayRequestException;
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
	
	private static final String GET_REPOSITORIES_BY_OWNER = buildUrl("/users/{user}/repos");
	
	private static final String GET_USERS_BY_KEYWORD = buildUrl("/legacy/user/search/{keyword}");
	

	/**
     * {@inheritDoc}
     */
	@Override
	@Cacheable("searchRepositoriesByKeyword")
	public List<Repository> searchRepositoriesByKeyword(String keyword) {
		Validate.notEmpty(keyword, "Keyword must be null and not empty");
		GitHubRepositories gitHubRepositories = getGitHttpClient().executeGetRequest(GET_REPOSITORIES_BY_KEYWORD, GitHubRepositories.class, keyword);
		List<Repository> repositories = new ArrayList<>();
		for (GitHubRepositorySearch gr : gitHubRepositories.getRepositories()) {
			Repository repository = DtoToModel.getRepository(gr);
			repositories.add(repository);
		}
		return repositories;

	}

	/**
     * {@inheritDoc}
     */
	@Override
	@Cacheable("searchRepositoryNames")
	public List<String> searchRepositoryNames(String keyword, int limit) {
		Validate.notEmpty(keyword, "Keyword must be null and not empty");
		GitHubRepositories gitHubRepositories = getGitHttpClient().executeGetRequest(GET_REPOSITORIES_BY_KEYWORD, GitHubRepositories.class, keyword);
		List<String> repositoryNames = new ArrayList<>();
		int i = 0;
		for (GitHubRepositorySearch gr : gitHubRepositories.getRepositories()) {
			if(i==limit){
				break;
			}
			if(gr.getName().toLowerCase().startsWith(keyword.toLowerCase()) && !repositoryNames.contains(gr.getName())){
				repositoryNames.add(gr.getName().toLowerCase());
				i++;
			}
		}
		Collections.sort(repositoryNames);
		return repositoryNames;
	}

	/**
     * {@inheritDoc}
     */
	@Override
	@Cacheable("searchRepositoriesByOwner")
	public List<Repository> searchRepositoriesByOwner(String owner) {
		Validate.notEmpty(owner, "Owner must be null and not empty");
		GitHubRepository[] gitHubRepositories = null;
		List<Repository> repositories = new ArrayList<>();
		try{
			gitHubRepositories = getGitHttpClient().executeGetRequest(GET_REPOSITORIES_BY_OWNER, GitHubRepository[].class, owner);
		}catch(GitewayRequestException e){
			if(e.getStatusCode() == 404){
				return repositories;
			}
			throw e;
		}
		for (GitHubRepository gr : gitHubRepositories) {
			Repository repository = DtoToModel.getRepository(gr);
			repositories.add(repository);
		}
		return repositories;
	}

	@Override
	@Cacheable("searchUserNames")
	public List<String> searchUserNames(String keyword, int limit) {
		Validate.notEmpty(keyword, "Keyword must be null and not empty");
		GitHubUsers gitHubUsers = getGitHttpClient().executeGetRequest(GET_USERS_BY_KEYWORD, GitHubUsers.class, keyword);
		List<String> userNames = new ArrayList<>();
		int i = 0;
		for (GitHubUser gr : gitHubUsers.getUsers()) {
			if(i==limit){
				break;
			}
			if(gr.getLogin().toLowerCase().startsWith(keyword.toLowerCase()) && !userNames.contains(gr.getLogin())){
				userNames.add(gr.getLogin().toLowerCase());
				i++;
			}
		}
		Collections.sort(userNames);
		return userNames;
	}

}
