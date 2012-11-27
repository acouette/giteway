package org.kwet.giteway.service;

import java.util.List;

import org.kwet.giteway.model.Commit;
import org.kwet.giteway.model.CommitterActivity;
import org.kwet.giteway.model.TimelineData;

public interface StatisticsCalculator {

	List<CommitterActivity> calculateActivity(List<Commit> commits);
	
	void concatLittleCommiters(List<CommitterActivity> committerActivities);

	List<TimelineData> getTimeLine(List<Commit> commits);

	List<TimelineData> getTimeLine(List<Commit> commits, int sectionCount);

}