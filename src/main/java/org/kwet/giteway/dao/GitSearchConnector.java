package org.kwet.giteway.dao;

import java.util.List;

import org.kwet.giteway.model.Repository;

/**
 * The Interface GitSearchConnector provides an API to search for repositories
 * 
 * @author a.couette
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
	 * Search repository by owner.
	 *
	 * @param owner : the repository owner
	 * @return the matching repository list
	 */
	List<Repository> searchRepositoryByOwner(String owner);
	
	/**
	 * Search repository names by keyword.
	 * 
	 * @param keyword : the search keyword
	 * @param limit of repositories returned
	 * @return the matching repository list
	 */
	List<String> searchRepositoryNames(String keyword, int limit);
	
	/**
	 * Search repository names by keyword.
	 * 
	 * @param keyword : the search keyword
	 * @param limit of repositories returned
	 * @return the matching repository list
	 */
	List<String> searchUserNames(String user, int limit);

}
