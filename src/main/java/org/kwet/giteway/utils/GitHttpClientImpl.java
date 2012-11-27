package org.kwet.giteway.utils;

import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.codehaus.jackson.map.ObjectMapper;
import org.kwet.giteway.exception.GithubRequestException;
import org.springframework.web.util.UriTemplate;

public class GitHttpClientImpl implements GitHttpClient {

	private ObjectMapper objectMapper = new ObjectMapper();;

	private PoolingClientConnectionManager cm;

	public GitHttpClientImpl(PoolingClientConnectionManager cm) {
		this.cm = cm;
	}

	@Override
	public <T> T executeRequest(String uri, Class<T> responseType, Object... urlVariables) {
		try {

			// build URI
			UriTemplate uriTemplate = new UriTemplate(uri);
			URI expanded = uriTemplate.expand(urlVariables);


			HttpGet getRequest = new HttpGet(expanded);
			getRequest.addHeader("accept", "application/json");
			String proxyHost = System.getProperty("http.proxyHost");
			String proxyPort = System.getProperty("http.proxyPort");
			if (proxyHost != null && proxyPort != null) {
				HttpHost proxy = new HttpHost(proxyHost, Integer.valueOf(proxyPort));
				getRequest.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
			}
			getRequest.setURI(expanded);
			

			// Get a connection from the pool
			DefaultHttpClient httpClient = new DefaultHttpClient(cm);
			HttpResponse response = httpClient.execute(getRequest);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new GithubRequestException("HTTP error code : " + response.getStatusLine().getStatusCode() + ". Reason : "
						+ response.getStatusLine().getReasonPhrase() + ". Url : " + uri);
			}

			T returnValue = objectMapper.readValue(response.getEntity().getContent(), responseType);
			getRequest.releaseConnection();

			return returnValue;
		} catch (IOException e) {
			throw new GithubRequestException(e);
		}
	}

}
