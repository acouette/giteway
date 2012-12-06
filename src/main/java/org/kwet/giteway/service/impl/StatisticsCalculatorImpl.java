package org.kwet.giteway.service.impl;

import static ch.lambdaj.Lambda.filter;
import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.kwet.giteway.dao.GitRepositoryConnector;
import org.kwet.giteway.model.Commit;
import org.kwet.giteway.model.CommitterActivities;
import org.kwet.giteway.model.CommitterActivity;
import org.kwet.giteway.model.Repository;
import org.kwet.giteway.model.Timeline;
import org.kwet.giteway.model.TimelineInterval;
import org.kwet.giteway.model.User;
import org.kwet.giteway.service.StatisticsCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Class StatisticsCalculatorImpl.
 * 
 * @author a.couette
 * 
 */
@Service
public class StatisticsCalculatorImpl implements StatisticsCalculator {

	private static final int DEFAULT_SECTION_COUNT = 20;

	private static final int COMMIT_LIMIT = 100;

	private static final long MS_IN_A_DAY = 1000L * 60L * 60L * 24L;

	@Autowired
	private GitRepositoryConnector repositoryConnector;

	/**
     * {@inheritDoc}
     */
	@Override
	public CommitterActivities calculateActivity(Repository repository) {

		List<Commit> commitList = repositoryConnector.findCommits(repository, COMMIT_LIMIT);

		Validate.notNull(commitList, "commit list can not be null");

		// First : create a map to define the number of commits per user
		Map<User, Integer> totalByUser = new HashMap<>();
		for (Commit commit : commitList) {
			User committer = commit.getCommiter();

			if (committer == null) {
				committer = getUndefinedUser();
			}

			if (totalByUser.containsKey(committer)) {
				totalByUser.put(committer, totalByUser.get(committer) + 1);
			} else {
				totalByUser.put(committer, 1);
			}
		}
		
		int commitCount = commitList.size();

		// Convert the map to a list of CommitterActivity defining the percentage per user
		List<CommitterActivity> committerActivityList = new ArrayList<>();
		for (User user : totalByUser.keySet()) {
			int percentage = (totalByUser.get(user) * 100) / commitCount;
			committerActivityList.add(new CommitterActivity(user, percentage));
		}

		// Sorts the list by percentage
		Collections.sort(committerActivityList, new Comparator<CommitterActivity>() {
			@Override
			public int compare(CommitterActivity o1, CommitterActivity o2) {
				return o1.getPercentage() < o2.getPercentage() ? 1 : -1;
			}
		});

		CommitterActivities committerActivities = new CommitterActivities();
		committerActivities.setCommitterActivityList(committerActivityList);
		committerActivities.setCommitCount(commitCount);
		committerActivities.setRepository(repository);
		
		return committerActivities;
	}

	/**
	 * Builds an undefined user if none were found
	 * 
	 * @return
	 */
	private User getUndefinedUser() {
		User user = new User();
		user.setLogin("undefined");
		return user;
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public Timeline getTimeLine(Repository repository) {
		return getTimeLine(repository, DEFAULT_SECTION_COUNT);
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public Timeline getTimeLine(Repository repository, int sectionCount) {

		Validate.isTrue(sectionCount > 0, "sectionCount can not be null or negative");

		List<Commit> commitList = repositoryConnector.findCommits(repository, COMMIT_LIMIT);
		Validate.notNull(commitList, "commit list can not be null");

		Timeline timeline = new Timeline();

		List<TimelineInterval> timelineIntervals = new ArrayList<>();
		timeline.setTimelineIntervals(timelineIntervals);
		timeline.setCommitCount(commitList.size());

		if (commitList.isEmpty()) {
			return timeline;
		}


		// Sorts the commits (by date)
		Collections.sort(commitList);

		long firstCommit = commitList.get(0).getDate().getTime();
		long lastCommit = commitList.get(commitList.size() - 1).getDate().getTime();
		long timelineDays = (lastCommit - firstCommit) + 1;
		long intervalDays = timelineDays / sectionCount;
		if (timelineDays % sectionCount != 0) {
			intervalDays++;
		}

		for (int i = 0; i < sectionCount; i++) {
			// Define start/end of interval
			long startTimeInterval = firstCommit + i * intervalDays;
			long endTimeInterval = (firstCommit + (i + 1) * intervalDays) - 1;

			// Filter commitList which belong to this interval
			List<Commit> filtered = filter(
					having(on(Commit.class).getDate().getTime(), greaterThanOrEqualTo(startTimeInterval)).and(
							having(on(Commit.class).getDate().getTime(), lessThanOrEqualTo(endTimeInterval))), commitList);

			// Creates the intervals from calculated attributes if commits were found
			int commitCount = filtered.size();
			if (commitCount > 0) {
				timelineIntervals.add(new TimelineInterval(startTimeInterval, endTimeInterval, commitCount));
			}

		}

		timeline.setIntervalDays((double) intervalDays / MS_IN_A_DAY);
		timeline.setTimelineDays((double) timelineDays / MS_IN_A_DAY);
		timeline.setRepository(repository);

		return timeline;
	}

	public void setRepositoryConnector(GitRepositoryConnector repositoryConnector) {
		this.repositoryConnector = repositoryConnector;
	}

}
