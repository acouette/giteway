package org.kwet.giteway.github;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.codehaus.jackson.map.ObjectMapper;
import org.kwet.giteway.exception.GithubRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GitConnectorImpl implements GitConnector {

	@Autowired
	private HttpClient httpClient;
	
	@Autowired ObjectMapper objectMapper;

	public InputStream executeRequest(String url){
		try {
			HttpGet getRequest = new HttpGet(url);
			getRequest.addHeader("accept", "application/json");
			HttpResponse response = httpClient.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new GithubRequestException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}

			return response.getEntity().getContent();
		} catch (IOException e) {
			throw new GithubRequestException(e);
		}
	}
	
	
	public <T> T readValue(InputStream inputStream, Class<T> responseType) {

		try {
			return objectMapper.readValue(inputStream, responseType);
		} catch (IOException e) {
			throw new GithubRequestException(e);
		}

	}
	
}
