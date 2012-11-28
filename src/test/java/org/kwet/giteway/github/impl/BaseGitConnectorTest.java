package org.kwet.giteway.github.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.kwet.giteway.http.GitHttpClientImpl;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public abstract class BaseGitConnectorTest {

	protected GitHttpClientImpl gitHttpClient;
	
	private HttpClient httpClient;
	
	public BaseGitConnectorTest(){
		gitHttpClient = new GitHttpClientImpl();
	}

	protected void configureHttpClient(String url, String responseFile) throws IllegalStateException, IOException {
		
		
		httpClient = mock(HttpClient.class);
		gitHttpClient.setHttpClient(httpClient);
		HttpResponse httpResponse = mock(HttpResponse.class);
		StatusLine statusLine = mock(StatusLine.class);
		HttpEntity httpEntity = mock(HttpEntity.class);
		
		when(statusLine.getStatusCode()).thenReturn(200);
		when(httpResponse.getStatusLine()).thenReturn(statusLine);
		
		InputStream is = jsonResource(responseFile).getInputStream();
		when(httpEntity.getContent()).thenReturn(is);
		when(httpResponse.getEntity()).thenReturn(httpEntity);
		
		when(httpClient.execute(any(HttpGet.class))).thenReturn(httpResponse);
		
		/*
		 * MockRestServiceServer mockServer =
		 * MockRestServiceServer.createServer(restTemplate);
		 * mockServer.expect(requestTo(url)).andExpect(method(GET))
		 * .andRespond(withSuccess(jsonResource(responseFile),
		 * MediaType.APPLICATION_JSON));
		 */
	}

	protected Resource jsonResource(String filename) {
		return new ClassPathResource(filename + ".json", getClass());
	}

}
