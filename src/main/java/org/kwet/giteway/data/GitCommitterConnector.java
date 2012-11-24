package org.kwet.giteway.data;

import java.util.List;

import org.kwet.giteway.model.Committer;
import org.kwet.giteway.model.Repository;

public interface GitCommitterConnector {

	List<Committer> findByRepository(Repository repository);
	
}
