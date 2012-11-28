package org.kwet.giteway.service.impl;

import static ch.lambdaj.Lambda.filter;
import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kwet.giteway.model.Commit;
import org.kwet.giteway.model.CommitterActivity;
import org.kwet.giteway.model.TimelineData;
import org.kwet.giteway.service.StatisticsCalculator;
import org.springframework.stereotype.Service;

@Service
public class StatisticsCalculatorImpl implements StatisticsCalculator {

	@Override
	public List<CommitterActivity> calculateActivity(List<Commit> commits) {

		List<CommitterActivity> committerActivities = new ArrayList<>();

		if (commits == null || commits.isEmpty()) {
			return committerActivities;
		}

		double validCommits = 0;
		Map<String, Integer> totalByUser = new HashMap<>();
		for (Commit commit : commits) {
			if (commit.getCommitter() == null) {
				continue;
			}
			validCommits++;
			String login = commit.getCommitter().getLogin();

			if (totalByUser.containsKey(login)) {
				totalByUser.put(login, totalByUser.get(login) + 1);
			} else {
				totalByUser.put(login, 1);
			}
		}

		for (String login : totalByUser.keySet()) {
			int percentage = (int) ((totalByUser.get(login) / validCommits) * 100);
			committerActivities.add(new CommitterActivity(login, percentage));
		}

		Collections.sort(committerActivities, new Comparator<CommitterActivity>() {

			@Override
			public int compare(CommitterActivity o1, CommitterActivity o2) {
				return o1.getPercentage() > o2.getPercentage() ? 1 : 0;
			}
		});

		return committerActivities;
	}

	@Override
	public List<TimelineData> getTimeLine(List<Commit> commits) {
		int sectionCount = 10;
		return getTimeLine(commits, sectionCount);
	}

	@Override
	public List<TimelineData> getTimeLine(List<Commit> commits, int sectionCount) {

		List<TimelineData> results = new ArrayList<>();

		if (commits.isEmpty()) {
			return results;
		}

		Collections.sort(commits);

		long firstCommit = commits.get(0).getDate().getTime();
		long lastCommit = commits.get(commits.size() - 1).getDate().getTime();
		long range = lastCommit - firstCommit;
		long step = range / sectionCount;

		for (int i = 0; i < sectionCount; i++) {

			long startTimeSection = firstCommit + i * step;
			long endTimeSection;
			if (i != sectionCount - 1) {
				endTimeSection = firstCommit + (i + 1) * step;
			} else {
				endTimeSection = Long.MAX_VALUE;
			}

			List<Commit> filtered1 = filter(having(on(Commit.class).getDate().getTime(), greaterThanOrEqualTo(startTimeSection)), commits);
			List<Commit> filtered2 = filter(having(on(Commit.class).getDate().getTime(), lessThan(endTimeSection)), filtered1);

			int commitCount = filtered2.size();
			results.add(new TimelineData(startTimeSection, commitCount));

		}

		return results;
	}

}
