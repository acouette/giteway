package org.kwet.giteway.http;

public interface GitHttpClient {

	<T> T executeRequest(String uri, Class<T> responseType, Object... urlVariables);

}
