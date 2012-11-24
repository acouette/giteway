package org.kwet.giteway.data.impl;

import java.util.Arrays;
import java.util.List;

import org.kwet.giteway.data.AbstractGitConnector;
import org.kwet.giteway.data.GitCommitterConnector;
import org.kwet.giteway.model.Committer;
import org.kwet.giteway.model.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

@Component
public class GitCommitterConnectorImpl extends AbstractGitConnector implements GitCommitterConnector {

	@Autowired
	public GitCommitterConnectorImpl(RestOperations restOperations) {
		super(restOperations);
	}

	static final String GET_COMMITTERS_BY_REPOSITORY = BASE_GITHUB_URL+"/repos/{username}/{name}/collaborators";
	
	public List<Committer> findByRepository(Repository repository) {
		return Arrays.asList(restOperations.getForObject(GET_COMMITTERS_BY_REPOSITORY, Committer[].class, repository.getUsername(),repository.getName()));
	}

}
