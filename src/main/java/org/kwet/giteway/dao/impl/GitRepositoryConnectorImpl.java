package org.kwet.giteway.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.kwet.giteway.dao.GitRepositoryConnector;
import org.kwet.giteway.dao.dto.DtoToModel;
import org.kwet.giteway.dao.dto.GitHubCommit;
import org.kwet.giteway.dao.dto.GitHubRepository;
import org.kwet.giteway.dao.dto.GitHubUser;
import org.kwet.giteway.model.Commit;
import org.kwet.giteway.model.Repository;
import org.kwet.giteway.model.User;
import org.springframework.cache.annotation.Cacheable;
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

	@Override
	@Cacheable("repositories")
	public Repository find(String repositoryOwner, String repositoryName) {
		Validate.notEmpty(repositoryOwner, "Owner can not be empty or null");
		Validate.notEmpty(repositoryName, "Repository name can not be empty or null");
		GitHubRepository gitHubRepository = getGitHttpClient().executeGetRequest(GET_REPOSITORY, GitHubRepository.class, repositoryOwner,
				repositoryName);
		return DtoToModel.getRepository(gitHubRepository);
	}

	@Override
	@Cacheable("users")
	public List<User> findCollaborators(Repository repository) {
		checkRepository(repository);
		GitHubUser[] gitHubUsers = getGitHttpClient().executeGetRequest(GET_COLLABORATORS, GitHubUser[].class, repository.getOwner(), repository.getName());
		List<User> users = new ArrayList<>();
		for (GitHubUser gw : gitHubUsers) {
			User user = DtoToModel.getUser(gw);
			users.add(user);
		}
		return users;
	}

	@Override
	@Cacheable("commits")
	public List<Commit> findCommits(Repository repository, int limit) {

		checkRepository(repository);
		GitHubCommit[] commitWrappers = getGitHttpClient().executeGetRequest(GET_COMMITS, GitHubCommit[].class, repository.getOwner(), repository.getName(),
				limit);
		List<GitHubCommit> filteredWrappers = Arrays.asList(commitWrappers);

		List<Commit> commitList = new ArrayList<>();
		for (GitHubCommit gc : filteredWrappers) {
			Commit c = DtoToModel.getCommit(gc);
			commitList.add(c);
		}
		return commitList;
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
