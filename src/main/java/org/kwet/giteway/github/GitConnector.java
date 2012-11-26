package org.kwet.giteway.github;

import java.io.InputStream;

public interface GitConnector {

	public InputStream executeRequest(String url);
	
	public <T> T readValue(InputStream inputStream, Class<T> responseType);
	
}
