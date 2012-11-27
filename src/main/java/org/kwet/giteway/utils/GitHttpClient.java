package org.kwet.giteway.utils;

public interface GitHttpClient {

	<T> T executeRequest(String uri, Class<T> responseType, Object... urlVariables);

}
