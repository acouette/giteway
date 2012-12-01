package org.kwet.giteway.dao;

import java.util.List;

import org.kwet.giteway.model.Commit;
import org.kwet.giteway.model.Repository;
import org.kwet.giteway.model.User;

/**
 * The Interface GitRepositoryConnector provide an API to retrieve data about a particular
 * Repository
 * 
 * @author Antoine Couette
 * 
 */
public interface GitRepositoryConnector {

	/**
	 * Find a repository by owner and name
	 * 
	 * @param owner : the repository owner
	 * @param name : the repository name
	 * @return the matching repository
	 */
	Repository find(String owner, String name);

	/**
	 * Find collaborators by repository
	 * 
	 * @param owner : the repository owner
	 * @param name : the repository name
	 * @return the list of collaborators
	 */
	List<User> findCollaborators(String owner, String name);

	/**
	 * Find commits by repository
	 * 
	 * @param owner : the repository owner
	 * @param name : the repository name
	 * @param max : number of commits to return
	 * @return the list of commits
	 */
	List<Commit> findCommits(String owner, String name, int max);
}
