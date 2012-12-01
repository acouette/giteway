package org.kwet.giteway.dao;

import java.util.List;

import org.kwet.giteway.model.Repository;

/**
 * The Interface GitSearchConnector provides an API to search for repositories
 * 
 * @author Antoine Couette
 *
 */
public interface GitSearchConnector {

	/**
	 * Search repository by keyword.
	 *
	 * @param keyword : the search keyword
	 * @return the matching repository list
	 */
	List<Repository> searchRepositoryByKeyword(String keyword);
	
	/**
	 * Search repository names by keyword.
	 * 
	 * @param keyword : the search keyword
	 * @param limit of repositories returned
	 * @return the matching repository list
	 */
	List<String> searchRepositoryNames(String keyword, int limit);

}
