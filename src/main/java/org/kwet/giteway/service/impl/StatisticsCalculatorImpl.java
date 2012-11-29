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

import org.apache.commons.lang.Validate;
import org.kwet.giteway.model.Commit;
import org.kwet.giteway.model.CommitterActivity;
import org.kwet.giteway.model.TimelineChunk;
import org.kwet.giteway.service.StatisticsCalculator;
import org.springframework.stereotype.Service;

// TODO: Auto-generated Javadoc
/**
 * The Class StatisticsCalculatorImpl.
 * 
 * @author Antoine Couette
 *
 */
@Service
public class StatisticsCalculatorImpl implements StatisticsCalculator {

	/* (non-Javadoc)
	 * @see org.kwet.giteway.service.StatisticsCalculator#calculateActivity(java.util.List)
	 */
	@Override
	public List<CommitterActivity> calculateActivity(List<Commit> commits) {

		Validate.notNull(commits, "commits can not be null");
		

		Map<String, Integer> totalByUser = new HashMap<>();
		for (Commit commit : commits) {
			String login = commit.getLogin();

			if (totalByUser.containsKey(login)) {
				totalByUser.put(login, totalByUser.get(login) + 1);
			} else {
				totalByUser.put(login, 1);
			}
		}


		List<CommitterActivity> committerActivities = new ArrayList<>();
		for (String login : totalByUser.keySet()) {
			int percentage = (int) ((totalByUser.get(login)* 100) / commits.size());
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

	/* (non-Javadoc)
	 * @see org.kwet.giteway.service.StatisticsCalculator#getTimeLine(java.util.List)
	 */
	@Override
	public List<TimelineChunk> getTimeLine(List<Commit> commits) {
		int sectionCount = 20;
		return getTimeLine(commits, sectionCount);
	}

	/* (non-Javadoc)
	 * @see org.kwet.giteway.service.StatisticsCalculator#getTimeLine(java.util.List, int)
	 */
	@Override
	public List<TimelineChunk> getTimeLine(List<Commit> commits, int sectionCount) {

		Validate.notNull(commits, "commits can not be null");
		

		Collections.sort(commits);

		long firstCommit = commits.get(0).getDate().getTime();
		long lastCommit = commits.get(commits.size() - 1).getDate().getTime();
		long range = lastCommit - firstCommit;
		long step = range / sectionCount;


		List<TimelineChunk> results = new ArrayList<>();
		for (int i = 0; i < sectionCount; i++) {

			long startTimeSection = firstCommit + i * step;
			long endTimeSection;
			if (i != sectionCount - 1) {
				endTimeSection = firstCommit + (i + 1) * step;
			} else {
				endTimeSection = lastCommit + 1;
			}

			List<Commit> filtered1 = filter(having(on(Commit.class).getDate().getTime(), greaterThanOrEqualTo(startTimeSection)), commits);
			List<Commit> filtered2 = filter(having(on(Commit.class).getDate().getTime(), lessThan(endTimeSection)), filtered1);

			int commitCount = filtered2.size();
			results.add(new TimelineChunk(startTimeSection, endTimeSection-1, commitCount));

		}

		return results;
	}

}
