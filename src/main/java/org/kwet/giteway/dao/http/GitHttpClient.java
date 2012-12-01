package org.kwet.giteway.dao.http;

// TODO: Auto-generated Javadoc
/**
 * The Interface GitHttpClient provides an API to request over http
 * It encapsulate 2 aspects :
 * 	1. HTTP  
 * 		a. HttpConnection polling
 *  	b. Http Request
 *  	c. Response analyse
 *  2. Deserialization
 *  	a. Jackson (Json To Object)
 * 
 * @author Antoine Couette
 *
 */
public interface GitHttpClient {

	/**
	 * Execute request.
	 * This method
	 *
	 * @param <T> the generic type
	 * @param uri the uri
	 * @param responseType the response type
	 * @param urlVariables the url variables
	 * @return the t
	 */
	<T> T executeGetRequest(String uri, Class<T> responseType, Object... urlVariables);

}
