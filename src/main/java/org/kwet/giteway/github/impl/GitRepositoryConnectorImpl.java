package org.kwet.giteway.github.impl;

import java.io.InputStream;
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

	static final String GET_REPOSITORY = BASE_GITHUB_URL + "/repos/{owner}/{name}";

	static final String GET_COLLABORATORS = GET_REPOSITORY + "/collaborators";

	static final String GET_COMMITS = GET_REPOSITORY + "/commits?page=1&per_page=100";
	
	@Override
	public Repository find(String owner, String name) {
		InputStream is = gitConnector.executeRequest(GET_REPOSITORY.replace("{owner}", owner).replace("{name}", name));
		return gitConnector.readValue(is, Repository.class);
	}

	@Override
	public List<User> findCollaborators(Repository repository) {
		checkRepository(repository);
		InputStream is = gitConnector.executeRequest(GET_COLLABORATORS.replace("{owner}", repository.getOwner().getLogin()).replace("{name}", repository.getName()));
		return Arrays.asList(gitConnector.readValue(is, User[].class));
	}

	@Override
	public List<Commit> findCommits(Repository repository) {
		checkRepository(repository);
		InputStream is = gitConnector.executeRequest(GET_COMMITS.replace("{owner}", repository.getOwner().getLogin()).replace("{name}", repository.getName()));
		return Arrays.asList(gitConnector.readValue(is, Commit[].class));
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
