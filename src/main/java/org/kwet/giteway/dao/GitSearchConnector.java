package org.kwet.giteway.dao;

import java.util.List;

import org.kwet.giteway.model.Repository;

// TODO: Auto-generated Javadoc
/**
 * The Interface GitSearchConnector.
 * 
 * @author Antoine Couette
 *
 */
public interface GitSearchConnector {

	/**
	 * Search repository by keyword.
	 *
	 * @param keyword the keyword
	 * @return the list
	 */
	List<Repository> searchRepositoryByKeyword(String keyword);

}
