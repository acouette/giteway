package org.kwet.giteway.github;

import org.kwet.giteway.http.GitHttpClient;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractGitConnector {

	private static final String BASE_GITHUB_URL = "https://api.github.com";

	private static final String CREDENTIALS = "?client_id=afd1b5696c7f02a5393e&client_secret=bdeedc27efd87f502bd6b932781836f514da201a";

	protected static String buildUrl(String rawUrl) {
		return BASE_GITHUB_URL.concat(rawUrl).concat(CREDENTIALS);
	}

	@Autowired
	protected GitHttpClient gitHttpClient;

	public void setGitHttpClient(GitHttpClient gitHttpClient){
		this.gitHttpClient = gitHttpClient;
	}
	
}
