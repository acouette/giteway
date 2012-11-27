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
	public List<User> findCollaborators(Repository repository) {
		checkRepository(repository);
		return Arrays.asList(gitHttpClient.executeRequest(GET_COLLABORATORS, User[].class, repository.getOwner().getLogin(),
				repository.getName()));
	}

	@Override
	public List<Commit> findCommits(Repository repository) {
		checkRepository(repository);
		return Arrays.asList(gitHttpClient.executeRequest(GET_COMMITS, Commit[].class, repository.getOwner().getLogin(),
				repository.getName()));
	}

	private void checkRepository(Repository repository) {
		if (repository == null) {
			throw new IllegalArgumentException("Repository can not be null !");
		}
		if (repository.getOwner() == null) {
			throw new IllegalArgumentException("Owner can not be null !");
		}
	}
}
