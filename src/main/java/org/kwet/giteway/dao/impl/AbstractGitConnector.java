package org.kwet.giteway.dao.impl;

import org.codehaus.jackson.map.ObjectMapper;
import org.kwet.giteway.dao.http.GitHttpClient;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The Class AbstractGitConnector provides an abstract class to extends for GitHub Connectors
 * 
 * @author a.couette
 * 
 */
public class AbstractGitConnector {

	private static final String BASE_GITHUB_URL = "https://api.github.com";

	private static final String CREDENTIALS = "?client_id=afd1b5696c7f02a5393e&client_secret=bdeedc27efd87f502bd6b932781836f514da201a";

	protected ObjectMapper objectMapper = new ObjectMapper();
	
	
	protected <T> T readValue(String response, Class<T> clazz){
		try {
			return objectMapper.readValue(response, clazz);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * utility method used by subclasses to build there URLS. Abstract the base URL and credentials
	 * 
	 * @param rawUrl the raw url
	 * @return the string
	 */
	protected static String buildUrl(String rawUrl) {
		return BASE_GITHUB_URL.concat(rawUrl).concat(CREDENTIALS);
	}

	@Autowired
	private GitHttpClient gitHttpClient;

	
	protected GitHttpClient getGitHttpClient() {
		return gitHttpClient;
	}

	public void setGitHttpClient(GitHttpClient gitHttpClient) {
		this.gitHttpClient = gitHttpClient;
	}
	
	
	

}
