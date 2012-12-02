package org.kwet.giteway.service;

import org.kwet.giteway.model.CommitterActivities;
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
	 * @param repositoryOwner : the owner of the repository on which we want to calculate user's activity
	 * @param repositoryName : the repository name on which we want to calculate user's activity
	 * @return CommitterActivities
	 */
	CommitterActivities calculateActivity(String repositoryOwner, String repositoryName);

	/**
	 * Defines a timeline from the last and the first commit, splits it in sections, give the number
	 * of commits within each section.
	 * 
	 * @param repositoryOwner : the owner of the repository on which we want to calculate user's activity
	 * @param repositoryName : the repository name on which we want to calculate user's activity
	 * @return the time line
	 */
	Timeline getTimeLine(String repositoryOwner, String repositoryName);

	/**
	 * Defines a timeline from the last and the first commit, splits it in sections, give the number
	 * of commits within each section.
	 * 
	 * @param repositoryOwner : the owner of the repository on which we want to calculate user's activity
	 * @param repositoryName : the repository name on which we want to calculate user's activity
	 * @param sectionCount the number of section
	 * @return the time line
	 */
	Timeline getTimeLine(String repositoryOwner, String repositoryName, int sectionCount);

}