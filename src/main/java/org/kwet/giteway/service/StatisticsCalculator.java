package org.kwet.giteway.service;

import java.util.List;

import org.kwet.giteway.model.Commit;
import org.kwet.giteway.model.CommitterActivity;
import org.kwet.giteway.model.TimelineChunk;

/**
 * The Interface StatisticsCalculator.
 * 
 * @author Antoine Couette
 * 
 */
public interface StatisticsCalculator {

	/**
	 * Calculate committer activity (percentage of commit)
	 * 
	 * @param the list commits to process
	 * @return the list of percentage of commits per user
	 */
	List<CommitterActivity> calculateActivity(List<Commit> commits);

	/**
	 * Defines a timeline from the last and the first commit, splits it in sections, give the number
	 * of commits within each section.
	 * 
	 * @param the list commits to process
	 * @return the time line chunks
	 */
	List<TimelineChunk> getTimeLine(List<Commit> commits);

	/**
	 * Defines a timeline from the last and the first commit, splits it in sections, give the number
	 * of commits within each section.
	 * 
	 * @param the list commits to process
	 * @param sectionCount the number of section
	 * @return the time line chunks
	 */
	List<TimelineChunk> getTimeLine(List<Commit> commits, int sectionCount);

}