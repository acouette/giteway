package org.kwet.giteway.data.impl;

import java.util.Arrays;
import java.util.List;

import org.kwet.giteway.data.AbstractGitConnector;
import org.kwet.giteway.data.GitRepositoryConnector;
import org.kwet.giteway.model.Commit;
import org.kwet.giteway.model.User;
import org.kwet.giteway.model.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

@Component
public class GitRepositoryConnectorImpl extends AbstractGitConnector implements GitRepositoryConnector {

	@Autowired
	public GitRepositoryConnectorImpl(RestOperations restOperations) {
		super(restOperations);
	}

	static final String GET_REPOSITORY = BASE_GITHUB_URL + "/repos/{owner}/{name}";

	static final String GET_COLLABORATORS = GET_REPOSITORY + "/collaborators";

	static final String GET_COMMITS = GET_REPOSITORY + "/commits?page=1&per_page=100";

	@Override
	public Repository find(String owner, String name) {
		return restOperations.getForObject(GET_REPOSITORY, Repository.class, owner, name);
	}

	@Override
	public List<User> findCollaborators(Repository repository) {
		checkRepository(repository);
		return Arrays.asList(restOperations.getForObject(GET_COLLABORATORS, User[].class, repository.getOwner().getLogin(),
				repository.getName()));
	}

	@Override
	public List<Commit> findCommits(Repository repository) {
		checkRepository(repository);
		return Arrays.asList(restOperations.getForObject(GET_COMMITS, Commit[].class, repository.getOwner().getLogin(),
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
