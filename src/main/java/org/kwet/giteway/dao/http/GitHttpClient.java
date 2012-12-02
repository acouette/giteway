package org.kwet.giteway.dao.http;

/**
 * The Interface GitHttpClient provides an API to request over http
 * It supports HttpConnection polling
 * 
 * @author a.couette
 *
 */
public interface GitHttpClient {

	/**
	 * Execute http get request, analyse the repsonse and deserialize the message thanks to jackson
	 *
	 * @param uri the uri to request
	 * @param responseType the response type
	 * @param urlParameters the url parameters to replace in the string
	 * @return the deserialized object
	 */
	<T> T executeGetRequest(String uri, Class<T> responseType, Object... urlParameters);

}
