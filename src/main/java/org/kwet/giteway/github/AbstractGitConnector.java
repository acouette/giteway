package org.kwet.giteway.github;

import org.springframework.beans.factory.annotation.Autowired;

public class AbstractGitConnector {

	protected static final String BASE_GITHUB_URL = "https://api.github.com";

	@Autowired
	protected GitConnector gitConnector;

}
