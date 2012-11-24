package org.kwet.giteway.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestOperations;

public class AbstractGitConnector {

	protected static final String BASE_GITHUB_URL = "https://api.github.com";

	protected RestOperations restOperations;

	@Autowired
	public AbstractGitConnector(RestOperations restOperations) {
		this.restOperations = restOperations;
	}

}
