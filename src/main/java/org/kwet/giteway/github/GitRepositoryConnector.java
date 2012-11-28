package org.kwet.giteway.github;

import java.util.List;

import org.kwet.giteway.model.Commit;
import org.kwet.giteway.model.Repository;
import org.kwet.giteway.model.User;

public interface GitRepositoryConnector {

	Repository find(String owner, String name);

	List<User> findCollaborators(String owner, String name);

	List<Commit> findCommits(String owner, String name);
}
