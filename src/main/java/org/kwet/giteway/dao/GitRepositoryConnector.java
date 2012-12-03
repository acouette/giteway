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
	 * Find collaborators by repository. Also Populates the repository with the returned list
	 * 
	 * @param repository
	 * @return the list of collaborators
	 */
	List<User> findCollaborators(Repository repository);

	/**
	 * Find commits by repository
	 * 
	 * @param repository
	 * @param limit : number of commits to return
	 * @return the list of commits
	 */
	List<Commit> findCommits(Repository repository, int limit);
}
