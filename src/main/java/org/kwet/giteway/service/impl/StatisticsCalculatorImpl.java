package org.kwet.giteway.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kwet.giteway.data.GitRepositoryConnector;
import org.kwet.giteway.model.Commit;
import org.kwet.giteway.model.Repository;
import org.kwet.giteway.model.User;
import org.kwet.giteway.service.StatisticsCalculator;

public class StatisticsCalculatorImpl implements StatisticsCalculator {

	private GitRepositoryConnector gitRepositoryConnector;

	public StatisticsCalculatorImpl(GitRepositoryConnector gitRepositoryConnector) {
		super();
		this.gitRepositoryConnector = gitRepositoryConnector;
	}

	@Override
	public Map<User, Double> calculateActivity(Repository repository) {

		if (repository == null) {
			throw new IllegalArgumentException("repository can not be null !");
		}

		List<Commit> commits = gitRepositoryConnector.findCommits(repository);

		Map<User, Integer> totalByUser = new HashMap<>();
		Map<User, Double> percentageByUser = new HashMap<>();

		if (commits == null || commits.isEmpty()) {
			return percentageByUser;
		}

		for (Commit commit : commits) {
			User committer = commit.getCommitter();
			if (totalByUser.containsKey(committer)) {
				totalByUser.put(committer, totalByUser.get(committer) + 1);
			} else {
				totalByUser.put(committer, 1);
			}
		}

		for (User user : totalByUser.keySet()) {
			percentageByUser.put(user, totalByUser.get(user) / (double) commits.size());
		}

		return percentageByUser;
	}

}
