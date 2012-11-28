package org.kwet.giteway.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.codehaus.jackson.map.ObjectMapper;
import org.kwet.giteway.exception.GithubRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriTemplate;

public class GitHttpClientImpl implements GitHttpClient {

	private static final Logger logger = LoggerFactory.getLogger(GitHttpClientImpl.class);

	private HttpClient httpClient;

	public GitHttpClientImpl() {
	}

	public GitHttpClientImpl(PoolingClientConnectionManager cm) {
		this.httpClient = new DefaultHttpClient(cm);
	}

	@Override
	public <T> T executeRequest(String uriString, Class<T> responseType, Object... urlVariables) {

		// create the request
		URI uri = buildURI(uriString, urlVariables);
		HttpGet getRequest = new HttpGet(uri);
		getRequest.addHeader("accept", "application/json");
		String proxyHost = System.getProperty("http.proxyHost");
		String proxyPort = System.getProperty("http.proxyPort");
		if (proxyHost != null && proxyPort != null) {
			HttpHost proxy = new HttpHost(proxyHost, Integer.valueOf(proxyPort));
			getRequest.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		}

		InputStream inputStream = null;
		T returnValue = null;
		try {
			// execute the request
			HttpResponse response = httpClient.execute(getRequest);

			// Check the status
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() != 200) {
				throw new GithubRequestException("HTTP error code : " + statusLine.getStatusCode() + ". Reason : "
						+ statusLine.getReasonPhrase() + ". Uri : " + uri);
			}

			// Parse the response
			HttpEntity entity = response.getEntity();
			inputStream = entity.getContent();
			returnValue = new ObjectMapper().readValue(inputStream, responseType);
		} catch (IOException e) {
			throw new GithubRequestException(e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.warn("Could not close the InputStream");
				}
			}
			getRequest.releaseConnection();
		}
		return returnValue;

	}

	// build URI
	protected URI buildURI(String uri, Object... urlVariables) {
		UriTemplate uriTemplate = new UriTemplate(uri);
		return uriTemplate.expand(urlVariables);
	}

	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

}
