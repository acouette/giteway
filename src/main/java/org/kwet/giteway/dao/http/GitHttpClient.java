package org.kwet.giteway.dao.http;

// TODO: Auto-generated Javadoc
/**
 * The Interface GitHttpClient.
 * 
 * @author Antoine Couette
 *
 */
public interface GitHttpClient {

	/**
	 * Execute request.
	 *
	 * @param <T> the generic type
	 * @param uri the uri
	 * @param responseType the response type
	 * @param urlVariables the url variables
	 * @return the t
	 */
	<T> T executeRequest(String uri, Class<T> responseType, Object... urlVariables);

}
