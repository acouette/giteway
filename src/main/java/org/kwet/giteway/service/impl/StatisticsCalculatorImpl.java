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
import org.kwet.giteway.model.Commit;
import org.kwet.giteway.model.CommitterActivity;
import org.kwet.giteway.model.TimelineChunk;
import org.kwet.giteway.model.User;
import org.kwet.giteway.service.StatisticsCalculator;
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
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kwet.giteway.service.StatisticsCalculator#calculateActivity(java.util.List)
	 */
	@Override
	public List<CommitterActivity> calculateActivity(List<Commit> commits) {

		Validate.notNull(commits, "commits can not be null");
		
		// First : create a map to define the number of commits per user
		Map<User, Integer> totalByUser = new HashMap<>();
		for (Commit commit : commits) {
			User committer = commit.getCommiter();
			
			if(committer==null){
				committer = getUndefinedUser();
			}
			
			if (totalByUser.containsKey(committer)) {
				totalByUser.put(committer, totalByUser.get(committer) + 1);
			} else {
				totalByUser.put(committer, 1);
			}
		}

		// Convert the map to a list of CommitterActivity defining the percentage per user
		List<CommitterActivity> committerActivities = new ArrayList<>();
		for (User user : totalByUser.keySet()) {
			int percentage = (totalByUser.get(user) * 100) / commits.size();
			committerActivities.add(new CommitterActivity(user, percentage));
		}

		// Sorts the list by percentage
		Collections.sort(committerActivities, new Comparator<CommitterActivity>() {
			@Override
			public int compare(CommitterActivity o1, CommitterActivity o2) {
				return o1.getPercentage() < o2.getPercentage() ? 1 : -1;
			}
		});

		return committerActivities;
	}
	
	/**
	 * Builds an undefined user if none were found
	 * @return
	 */
	private User getUndefinedUser(){
		User user = new User();
		user.setLogin("undefined");
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kwet.giteway.service.StatisticsCalculator#getTimeLine(java.util.List)
	 */
	@Override
	public List<TimelineChunk> getTimeLine(List<Commit> commits) {
		return getTimeLine(commits, DEFAULT_SECTION_COUNT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kwet.giteway.service.StatisticsCalculator#getTimeLine(java.util.List, int)
	 */
	@Override
	public List<TimelineChunk> getTimeLine(List<Commit> commits, int sectionCount) {

		
		Validate.isTrue(sectionCount>0,"sectionCount can not be null or negative");
		Validate.notNull(commits, "commits can not be null");

		List<TimelineChunk> results = new ArrayList<>();
		
		if(commits.isEmpty()){
			return results;
		}
		
		// Sorts the commits (by date)
		Collections.sort(commits);

		long firstCommit = commits.get(0).getDate().getTime();
		long lastCommit = commits.get(commits.size() - 1).getDate().getTime();
		long timelineDuration = (lastCommit - firstCommit)+1;
		long chunkDuration = timelineDuration / sectionCount;
		if(timelineDuration%sectionCount!=0){
			chunkDuration++;
		}
		
		
		for (int i = 0; i < sectionCount; i++) {
			// Define start/end of chunk
			long startTimeChunk = firstCommit + i * chunkDuration;
			long endTimeChunk = (firstCommit + (i + 1) * chunkDuration) - 1;
			
			
			// Filter commits which belong to this chunk
			List<Commit> filtered = filter(
					having(on(Commit.class).getDate().getTime(), greaterThanOrEqualTo(startTimeChunk)).and(
							having(on(Commit.class).getDate().getTime(), lessThanOrEqualTo(endTimeChunk))), commits);

			// Creates the chunks from calculated attributes if commits were found
			int commitCount = filtered.size();
			if(commitCount>0){
				results.add(new TimelineChunk(startTimeChunk, endTimeChunk , commitCount));
			}

		}

		return results;
	}

	private static final long MS_IN_A_DAY = 1000L*60L*60L*24L;
	
	@Override
	public double getChunkDurationInDays(TimelineChunk timelineChunk) {
		long duration = timelineChunk.getEnd() - timelineChunk.getStart();
		return (double)duration / MS_IN_A_DAY;
	}

	@Override
	public double getTimeLineDurationInDays(List<TimelineChunk> timelineChunks) {
		
		long start = timelineChunks.get(0).getStart();
		long end = timelineChunks.get(timelineChunks.size()-1).getEnd();
		return (double)(end-start)/MS_IN_A_DAY;
	}

}
