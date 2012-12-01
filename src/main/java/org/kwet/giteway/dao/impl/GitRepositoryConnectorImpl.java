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
import org.springframework.stereotype.Component;

// TODO: Auto-generated Javadoc
/**
 * The Class GitRepositoryConnectorImpl.
 * 
 * @author a.couette
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
	public Repository find(String repositoryOwner, String repositoryName) {
		checkRepository(repositoryOwner, repositoryName);
		GitHubRepository gitHubRepository = getGitHttpClient().executeGetRequest(GET_REPOSITORY, GitHubRepository.class, repositoryOwner,
				repositoryName);
		return DtoToModel.getRepository(gitHubRepository);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kwet.giteway.dao.GitRepositoryConnector#findCollaborators(java.lang .String,
	 * java.lang.String)
	 */
	@Override
	public List<User> findCollaborators(String repositoryOwner, String repositoryName) {
		checkRepository(repositoryOwner, repositoryName);
		GitHubUser[] gitHubUsers = getGitHttpClient().executeGetRequest(GET_COLLABORATORS, GitHubUser[].class, repositoryOwner, repositoryName);
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
	 * @see org.kwet.giteway.dao.GitRepositoryConnector#findCommits(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<Commit> findCommits(String repositoryOwner, String repositoryName, int limit) {
		checkRepository(repositoryOwner, repositoryName);
		GitHubCommit[] commitWrappers = getGitHttpClient().executeGetRequest(GET_COMMITS, GitHubCommit[].class, repositoryOwner, repositoryName,
				limit);
		List<GitHubCommit> filteredWrappers = Arrays.asList(commitWrappers);

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
	 * @param owner the owner
	 * @param name the name
	 */
	private void checkRepository(String repositoryOwner, String repositoryName) {
		Validate.notEmpty(repositoryOwner, "Owner can not be empty or null");
		Validate.notEmpty(repositoryName, "Repository name can not be empty or null");
	}
}
