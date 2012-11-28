package org.kwet.giteway.github.impl;

import java.util.Arrays;
import java.util.List;

import org.kwet.giteway.github.AbstractGitConnector;
import org.kwet.giteway.github.GitRepositoryConnector;
import org.kwet.giteway.model.Commit;
import org.kwet.giteway.model.Repository;
import org.kwet.giteway.model.User;
import org.springframework.stereotype.Component;

@Component
public class GitRepositoryConnectorImpl extends AbstractGitConnector implements GitRepositoryConnector {

	static final String GET_BASE = "/repos/{owner}/{name}";

	static final String GET_REPOSITORY = buildUrl(GET_BASE);

	static final String GET_COLLABORATORS = buildUrl(GET_BASE + "/collaborators");

	static final String GET_COMMITS = buildUrl(GET_BASE + "/commits") + "&page=1&per_page=100";

	@Override
	public Repository find(String owner, String name) {
		return gitHttpClient.executeRequest(GET_REPOSITORY, Repository.class, owner, name);
	}

	@Override
	public List<User> findCollaborators(String owner, String name) {
		checkRepository(owner, name);
		return Arrays.asList(gitHttpClient.executeRequest(GET_COLLABORATORS, User[].class, owner, name));
	}

	@Override
	public List<Commit> findCommits(String owner, String name) {
		checkRepository(owner, name);
		return Arrays.asList(gitHttpClient.executeRequest(GET_COMMITS, Commit[].class, owner, name));
	}

	private void checkRepository(String owner, String name) {
		if (owner == null) {
			throw new IllegalArgumentException("owner can not be null !");
		}
		if (name == null) {
			throw new IllegalArgumentException("name can not be null !");
		}
	}
}
