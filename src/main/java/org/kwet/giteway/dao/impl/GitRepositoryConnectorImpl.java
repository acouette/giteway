package org.kwet.giteway.dao.impl;

import static ch.lambdaj.Lambda.convert;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.kwet.giteway.dao.GitRepositoryConnector;
import org.kwet.giteway.dao.converter.CommitConverter;
import org.kwet.giteway.dao.converter.UserConverter;
import org.kwet.giteway.model.Commit;
import org.kwet.giteway.model.Repository;
import org.kwet.giteway.model.User;
import org.springframework.stereotype.Component;

/**
 * The Class GitRepositoryConnectorImpl.
 * 
 * @author a.couette
 * 
 */
@Component
public class GitRepositoryConnectorImpl extends AbstractGitConnector implements GitRepositoryConnector {

	private static final String GET_BASE = "/repos/{owner}/{name}";

	private static final String GET_REPOSITORY = buildUrl(GET_BASE);

	private static final String GET_COLLABORATORS = buildUrl(GET_BASE + "/collaborators");

	private static final String GET_COMMITS = buildUrl(GET_BASE + "/commits") + "&page=1&per_page={count}";
	
	
	/**
     * {@inheritDoc}
     */
	@SuppressWarnings("unchecked")
	@Override
	public Repository find(String repositoryOwner, String repositoryName) {
		Validate.notEmpty(repositoryOwner, "Owner can not be empty or null");
		Validate.notEmpty(repositoryName, "Repository name can not be empty or null");
		Map<String,Object> reposMap  = readValue(getGitHttpClient().executeGetRequest(GET_REPOSITORY,  repositoryOwner, repositoryName), Map.class);
		Repository repository = new Repository();
		repository.setName((String) reposMap.get("name"));
		repository.setDescription((String) reposMap.get("description"));
		Map<String, Object> owner = (Map<String, Object>) reposMap.get("owner");
		repository.setOwner((String)owner.get("login"));
		return repository;
	}

	/**
     * {@inheritDoc}
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> findCollaborators(Repository repository) {
		checkRepository(repository);
		List<Map<String,Object>> usersList = readValue(getGitHttpClient().executeGetRequest(GET_COLLABORATORS, repository.getOwner(), repository.getName()),List.class);
		return convert(usersList, new UserConverter());
	}

	/**
     * {@inheritDoc}
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<Commit> findCommits(Repository repository, int limit) {

		checkRepository(repository);
		List<Map<String,Object>> commitsList = readValue(getGitHttpClient().executeGetRequest(GET_COMMITS, repository.getOwner(), repository.getName(), limit),List.class);
		return convert(commitsList, new CommitConverter());
	}
	

	/**
	 * Check repository.
	 * 
	 * @param repository
	 */
	private void checkRepository(Repository repository) {
		Validate.notNull(repository, "Repository can not null");
		Validate.notEmpty(repository.getOwner(), "Owner can not be empty or null");
		Validate.notEmpty(repository.getName(), "Repository name can not be empty or null");
	}
}
