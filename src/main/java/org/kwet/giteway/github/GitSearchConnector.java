package org.kwet.giteway.github;

import java.util.List;

import org.kwet.giteway.model.RepositorySearch;

public interface GitSearchConnector {

	List<RepositorySearch> searchRepositoryByKeyword(String keyword);

}
