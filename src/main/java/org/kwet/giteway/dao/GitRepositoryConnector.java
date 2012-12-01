package org.kwet.giteway.dao;

import java.util.List;

import org.kwet.giteway.model.Commit;
import org.kwet.giteway.model.Repository;
import org.kwet.giteway.model.User;

/**
 * The Interface GitRepositoryConnector provide an API to retrieve data about a particular
 * Repository
 * 
 * @author a.couette
 * 
 */
public interface GitRepositoryConnector {

	/**
	 * Find a repository by owner and name
	 * 
	 * @param repositoryOwner : the repository owner
	 * @param repositoryName : the repository name
	 * @return the matching repository
	 */
	Repository find(String repositoryOwner, String repositoryName);

	/**
	 * Find collaborators by repository
	 * 
	 * @param repositoryOwner : the repository owner
	 * @param repositoryName : the repository name
	 * @return the list of collaborators
	 */
	List<User> findCollaborators(String repositoryOwner, String repositoryName);

	/**
	 * Find commits by repository
	 * 
	 * @param repositoryOwner : the repository owner
	 * @param repositoryName : the repository name
	 * @param limit : number of commits to return
	 * @return the list of commits
	 */
	List<Commit> findCommits(String repositoryOwner, String repositoryName, int limit);
}
