package org.kwet.giteway.dao.impl;

import static ch.lambdaj.Lambda.filter;
import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;
import static org.hamcrest.Matchers.notNullValue;

import java.util.ArrayList;
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
import org.springframework.stereotype.Component;

// TODO: Auto-generated Javadoc
/**
 * The Class GitRepositoryConnectorImpl.
 * 
 * @author Antoine Couette
 * 
 */
@Component
public class GitRepositoryConnectorImpl extends AbstractGitConnector implements GitRepositoryConnector {

	static final String GET_BASE = "/repos/{owner}/{name}";

	static final String GET_REPOSITORY = buildUrl(GET_BASE);

	static final String GET_COLLABORATORS = buildUrl(GET_BASE + "/collaborators");

	static final String GET_COMMITS = buildUrl(GET_BASE + "/commits") + "&page=1&per_page={count}";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kwet.giteway.dao.GitRepositoryConnector#find(java.lang.String, java.lang.String)
	 */
	@Override
	public Repository find(String owner, String name) {
		checkRepository(owner, name);
		GitHubRepository gitHubRepository = gitHttpClient.executeGetRequest(GET_REPOSITORY, GitHubRepository.class, owner, name);
		return DtoToModel.getRepository(gitHubRepository);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kwet.giteway.dao.GitRepositoryConnector#findCollaborators(java.lang .String, java.lang.String)
	 */
	@Override
	public List<User> findCollaborators(String owner, String name) {
		checkRepository(owner, name);
		GitHubUser[] gitHubUsers = gitHttpClient.executeGetRequest(GET_COLLABORATORS, GitHubUser[].class, owner, name);
		List<User> users = new ArrayList<>();
		for (GitHubUser gw : gitHubUsers) {
			User user = DtoToModel.getUser(gw);
			users.add(user);
		}

		return users;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kwet.giteway.dao.GitRepositoryConnector#findCommits(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Commit> findCommits(String owner, String name, int max) {
		checkRepository(owner, name);
		GitHubCommit[] commitWrappers = gitHttpClient.executeGetRequest(GET_COMMITS, GitHubCommit[].class, owner, name, max);
		List<GitHubCommit> filteredWrappers = filter(
				having(on(GitHubCommit.class).getCommit(), notNullValue()).and(having(on(GitHubCommit.class).getCommitter(), notNullValue())),
				commitWrappers);

		List<Commit> commits = new ArrayList<>();
		for (GitHubCommit gc : filteredWrappers) {
			Commit c = DtoToModel.getCommit(gc);
			commits.add(c);
		}

		return commits;
	}

	/**
	 * Check repository.
	 * 
	 * @param owner
	 *            the owner
	 * @param name
	 *            the name
	 */
	private void checkRepository(String owner, String name) {
		Validate.notEmpty(owner, "Owner can not be empty or null");
		Validate.notEmpty(owner, "Repository name can not be empty or null");
	}
}
