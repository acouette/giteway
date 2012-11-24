package org.kwet.giteway.data;

import java.util.List;

import org.kwet.giteway.model.Repository;

public interface GitRepositoryConnector {

	List<Repository> findByKeyword(String keyword);
	
}
