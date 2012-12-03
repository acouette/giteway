package org.kwet.giteway.service;

import org.kwet.giteway.model.CommitterActivities;
import org.kwet.giteway.model.Repository;
import org.kwet.giteway.model.Timeline;

/**
 * The Interface StatisticsCalculator.
 * 
 * @author a.couette
 * 
 */
public interface StatisticsCalculator {

	/**
	 * Calculate committer activity (percentage of commit)
	 * 
	 * @param repository :  the repository on which we want to calculate user's activity
	 * @return CommitterActivities
	 */
	CommitterActivities calculateActivity(Repository repository);

	/**
	 * Defines a timeline from the last and the first commit, splits it in sections, give the number
	 * of commits within each section.
	 * 
	 * @param repository :  the repository on which we want to calculate the timeline
	 * @return the time line
	 */
	Timeline getTimeLine(Repository repository);

	/**
	 * Defines a timeline from the last and the first commit, splits it in sections, give the number
	 * of commits within each section.
	 * 
	 * @param repository :  the repository on which we want to calculate the timeline
	 * @param sectionCount the number of section
	 * @return the time line
	 */
	Timeline getTimeLine(Repository repository, int sectionCount);

}