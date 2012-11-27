package org.kwet.giteway.github;

import org.kwet.giteway.utils.GitHttpClient;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractGitConnector {

	private static final String BASE_GITHUB_URL = "https://api.github.com";

	private static final String CREDENTIALS = "?client_id=afd1b5696c7f02a5393e&client_secret=f9deaacf98e145412b166bda66445743d381d1a1";

	protected static String buildUrl(String rawUrl) {
		return BASE_GITHUB_URL.concat(rawUrl).concat(CREDENTIALS);
	}

	@Autowired
	protected GitHttpClient gitHttpClient;

}
