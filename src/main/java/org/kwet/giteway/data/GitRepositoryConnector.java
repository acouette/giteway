package org.kwet.giteway.data;

import java.util.List;

import org.kwet.giteway.model.Commit;
import org.kwet.giteway.model.Repository;
import org.kwet.giteway.model.User;

public interface GitRepositoryConnector {

	Repository find(String owner, String name);

	List<User> findCollaborators(Repository repository);

	List<Commit> findCommits(Repository repository);
}
