/*
 * 
 */
package org.kwet.giteway.dao.http;

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
import org.kwet.giteway.model.GitewayRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriTemplate;

/**
 * The Class GitHttpClientImpl.
 * 
 * @author a.couette
 *
 */
public class GitHttpClientImpl implements GitHttpClient {

	
	private static final Logger LOG = LoggerFactory.getLogger(GitHttpClientImpl.class);

	
	private HttpClient httpClient;

	
	public GitHttpClientImpl() {
	}

	
	public GitHttpClientImpl(PoolingClientConnectionManager cm) {
		this.httpClient = new DefaultHttpClient(cm);
	}

	/* (non-Javadoc)
	 * @see org.kwet.giteway.http.GitHttpClient#executeRequest(java.lang.String, java.lang.Class, java.lang.Object[])
	 */
	@Override
	public <T> T executeGetRequest(String uriString, Class<T> responseType, Object... urlVariables) {

		// create the request
		URI uri = buildURI(uriString, urlVariables);
		HttpGet getRequest = buildRequest(uri);

		InputStream inputStream = null;
		T returnValue = null;
		try {
			// execute the request
			HttpResponse response = httpClient.execute(getRequest);

			// Check the status
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() != 200) {
				throw new GitewayRequestException("HTTP error code : " + statusLine.getStatusCode() + ". Reason : "
						+ statusLine.getReasonPhrase() + ". Uri : " + uri);
			}

			// Parse the response
			HttpEntity entity = response.getEntity();
			inputStream = entity.getContent();
			returnValue = new ObjectMapper().readValue(inputStream, responseType);
		} catch (IOException e) {
			throw new GitewayRequestException(e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					LOG.warn("Could not close the InputStream");
				}
			}
			getRequest.releaseConnection();
		}
		return returnValue;

	}
	
	
	/**
	 * Builds the request.
	 *
	 * @param uri the uri
	 * @return the http get
	 */
	protected HttpGet buildRequest(URI uri){
		HttpGet getRequest = new HttpGet(uri);
		getRequest.addHeader("accept", "application/json");
		String proxyHost = System.getProperty("http.proxyHost");
		String proxyPort = System.getProperty("http.proxyPort");
		if (proxyHost != null && proxyPort != null) {
			HttpHost proxy = new HttpHost(proxyHost, Integer.valueOf(proxyPort));
			getRequest.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		}
		return getRequest;
	}

	
	/**
	 * Builds the uri.
	 *
	 * @param uri the uri
	 * @param urlVariables the url variables
	 * @return the uri
	 */
	protected URI buildURI(String uri, Object... urlVariables) {
		UriTemplate uriTemplate = new UriTemplate(uri);
		return uriTemplate.expand(urlVariables);
	}

}
