package org.kwet.giteway.dao;

import java.util.List;

import org.kwet.giteway.model.Commit;
import org.kwet.giteway.model.Repository;
import org.kwet.giteway.model.User;

// TODO: Auto-generated Javadoc
/**
 * The Interface GitRepositoryConnector.
 * 
 * @author Antoine Couette
 *
 */
public interface GitRepositoryConnector {

	/**
	 * Find.
	 *
	 * @param owner the owner
	 * @param name the name
	 * @return the repository
	 */
	Repository find(String owner, String name);

	/**
	 * Find collaborators.
	 *
	 * @param owner the owner
	 * @param name the name
	 * @return the list
	 */
	List<User> findCollaborators(String owner, String name);

	/**
	 * Find commits.
	 *
	 * @param owner the owner
	 * @param name the name
	 * @return the list
	 */
	List<Commit> findCommits(String owner, String name);
}
