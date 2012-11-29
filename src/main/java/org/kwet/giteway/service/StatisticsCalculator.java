package org.kwet.giteway.service;

import java.util.List;

import org.kwet.giteway.model.Commit;
import org.kwet.giteway.model.CommitterActivity;
import org.kwet.giteway.model.TimelineChunk;

// TODO: Auto-generated Javadoc
/**
 * The Interface StatisticsCalculator.
 * 
 * @author Antoine Couette
 *
 */
public interface StatisticsCalculator {

	/**
	 * Calculate activity.
	 *
	 * @param commits the commits
	 * @return the list
	 */
	List<CommitterActivity> calculateActivity(List<Commit> commits);

	/**
	 * Gets the time line.
	 *
	 * @param commits the commits
	 * @return the time line
	 */
	List<TimelineChunk> getTimeLine(List<Commit> commits);

	/**
	 * Gets the time line.
	 *
	 * @param commits the commits
	 * @param sectionCount the section count
	 * @return the time line
	 */
	List<TimelineChunk> getTimeLine(List<Commit> commits, int sectionCount);

}