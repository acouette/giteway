package org.kwet.giteway.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.kwet.giteway.dao.GitRepositoryConnector;
import org.kwet.giteway.model.Commit;
import org.kwet.giteway.model.CommitterActivities;
import org.kwet.giteway.model.CommitterActivity;
import org.kwet.giteway.model.Repository;
import org.kwet.giteway.model.Timeline;
import org.kwet.giteway.model.TimelineInterval;
import org.kwet.giteway.model.User;

public class StatisticsCalculatorTest {

	private List<Commit> commits;

	private StatisticsCalculatorImpl statisticsCalculator = new StatisticsCalculatorImpl();
	
	private GitRepositoryConnector gitRepositoryConnector;

	private User user1;

	private User user2;
	
	private static final String repositoryOwner = "springsource";
	
	private static final String repositoryName = "springframework";
	
	private static Repository repository;

	@Before
	public void before() {
		repository = new Repository(repositoryOwner,repositoryName);
		

		user1 = new User();
		user1.setLogin("couettos");

		user2 = new User();
		user2.setLogin("pipin");
		
		Commit commit1 = new Commit();
		commit1.setMessage("commit1");
		commit1.setCommiter(user1);
		commit1.setDate(new Date(0));

		Commit commit2 = new Commit();
		commit2.setMessage("commit2");
		commit2.setCommiter(user1);
		commit2.setDate(new Date(5));

		Commit commit3 = new Commit();
		commit3.setMessage("commit3");
		commit3.setCommiter(user1);
		commit3.setDate(new Date(20));

		Commit commit4 = new Commit();
		commit4.setMessage("commit4");
		commit4.setCommiter(user2);
		commit4.setDate(new Date(99));

		commits = new ArrayList<>();
		commits.add(commit1);
		commits.add(commit2);
		commits.add(commit3);
		commits.add(commit4);
		

	}

	@Test
	public void testCalculateActivity() {

		gitRepositoryConnector = mock(GitRepositoryConnector.class);
		when(gitRepositoryConnector.findCommits(repository, 100)).thenReturn(commits);
		statisticsCalculator.setRepositoryConnector(gitRepositoryConnector);
		
		
		CommitterActivities committerActivities = statisticsCalculator.calculateActivity(repository);

		assertNotNull(committerActivities);
		List<CommitterActivity> committerActivityList = committerActivities.getCommitterActivityList();
		assertNotNull(committerActivityList);
		assertEquals(2, committerActivityList.size());
		assertEquals(user1, committerActivityList.get(0).getCommitter());
		assertEquals(75, committerActivityList.get(0).getPercentage());
		assertEquals(user2, committerActivityList.get(1).getCommitter());
		assertEquals(25, committerActivityList.get(1).getPercentage());
		assertEquals(4, committerActivities.getCommitCount());
	}
	
	@Test
	public void testCalculateActivityWithUndefinedUser() {
		Commit commit5 = new Commit();
		commit5.setMessage("commit5");
		commit5.setCommiter(null);
		commit5.setDate(new Date(99));
		
		commits.add(commit5);
		gitRepositoryConnector = mock(GitRepositoryConnector.class);
		when(gitRepositoryConnector.findCommits(repository, 100)).thenReturn(commits);
		statisticsCalculator.setRepositoryConnector(gitRepositoryConnector);

		CommitterActivities committerActivities = statisticsCalculator.calculateActivity(repository);

		assertNotNull(committerActivities);
		List<CommitterActivity> committerActivityList = committerActivities.getCommitterActivityList();
		assertNotNull(committerActivityList);
		assertEquals(3, committerActivityList.size());
		assertEquals(user1, committerActivityList.get(0).getCommitter());
		assertEquals(60, committerActivityList.get(0).getPercentage());
		assertEquals("undefined", committerActivityList.get(1).getCommitter().getLogin());
		assertEquals(20, committerActivityList.get(1).getPercentage());
		assertEquals(user2, committerActivityList.get(2).getCommitter());
		assertEquals(20, committerActivityList.get(2).getPercentage());
		assertEquals(5, committerActivities.getCommitCount());
	}
	
	@Test
	public void testCalculateActivityOneCommit() {

		List<Commit> commitList = new ArrayList<>();

		Commit commit = new Commit();
		commit.setMessage("commit");
		commit.setCommiter(user2);
		commit.setDate(new Date(100));
		commitList.add(commit);
		
		
		gitRepositoryConnector = mock(GitRepositoryConnector.class);
		when(gitRepositoryConnector.findCommits(repository, 100)).thenReturn(commitList);
		statisticsCalculator.setRepositoryConnector(gitRepositoryConnector);
		
		CommitterActivities committerActivities = statisticsCalculator.calculateActivity(repository);

		assertNotNull(committerActivities);
		List<CommitterActivity> committerActivityList = committerActivities.getCommitterActivityList();
		assertNotNull(committerActivityList);
		assertEquals(1, committerActivityList.size());
		assertEquals(user2, committerActivityList.get(0).getCommitter());
		assertEquals(100, committerActivityList.get(0).getPercentage());
		assertEquals(1, committerActivities.getCommitCount());
	}

	@Test
	public void testGetTimeLine100ms() {

		gitRepositoryConnector = mock(GitRepositoryConnector.class);
		when(gitRepositoryConnector.findCommits(repository, 100)).thenReturn(commits);
		statisticsCalculator.setRepositoryConnector(gitRepositoryConnector);
		
		Timeline timeline = statisticsCalculator.getTimeLine(repository, 10);

		assertNotNull(timeline);
		List<TimelineInterval> timelineIntervals = timeline.getTimelineIntervals();
		assertNotNull(timelineIntervals);
		
		assertEquals(3, timelineIntervals.size());
		
		assertEquals(2, timelineIntervals.get(0).getCommitCount());
		assertEquals(0L, timelineIntervals.get(0).getStart());
		assertEquals(9L, timelineIntervals.get(0).getEnd());
		
		assertEquals(1L, timelineIntervals.get(1).getCommitCount());
		assertEquals(20L, timelineIntervals.get(1).getStart());
		assertEquals(29L, timelineIntervals.get(1).getEnd());
		
		assertEquals(1L, timelineIntervals.get(2).getCommitCount());
		assertEquals(90L, timelineIntervals.get(2).getStart());
		assertEquals(99L, timelineIntervals.get(2).getEnd());
	}
	
	@Test
	public void testGetTimeLine101ms() {
		
		Commit commit5 = new Commit();
		commit5.setMessage("commit4");
		commit5.setCommiter(user2);
		commit5.setDate(new Date(100));
		
		commits.add(commit5);
		
		gitRepositoryConnector = mock(GitRepositoryConnector.class);
		when(gitRepositoryConnector.findCommits(repository, 100)).thenReturn(commits);
		statisticsCalculator.setRepositoryConnector(gitRepositoryConnector);
		
		Timeline timeline = statisticsCalculator.getTimeLine(repository, 10);

		assertNotNull(timeline);
		List<TimelineInterval> timelineIntervals = timeline.getTimelineIntervals();
		assertNotNull(timelineIntervals);
		
		assertEquals(3, timelineIntervals.size());
		
		assertEquals(2, timelineIntervals.get(0).getCommitCount());
		assertEquals(0L, timelineIntervals.get(0).getStart());
		assertEquals(10L, timelineIntervals.get(0).getEnd());
		
		assertEquals(1L, timelineIntervals.get(1).getCommitCount());
		assertEquals(11L, timelineIntervals.get(1).getStart());
		assertEquals(21L, timelineIntervals.get(1).getEnd());
		
		assertEquals(2L, timelineIntervals.get(2).getCommitCount());
		assertEquals(99L, timelineIntervals.get(2).getStart());
		assertEquals(109L, timelineIntervals.get(2).getEnd());
	}
	
	@Test
	public void testGetTimeLineSectionOneElement() {
		List<Commit> commitList = new ArrayList<>();
		Commit commit = new Commit();
		commit.setMessage("commit");
		commit.setCommiter(user1);
		commit.setDate(new Date(1000));
		commitList.add(commit);
		
		gitRepositoryConnector = mock(GitRepositoryConnector.class);
		when(gitRepositoryConnector.findCommits(repository, 100)).thenReturn(commitList);
		statisticsCalculator.setRepositoryConnector(gitRepositoryConnector);
		
		Timeline timeline = statisticsCalculator.getTimeLine(repository, 10);
		
		assertNotNull(timeline);
		List<TimelineInterval> timelineIntervals = timeline.getTimelineIntervals();
		assertNotNull(timelineIntervals);
		
		assertEquals(1,timelineIntervals.size());
		TimelineInterval timelineInterval = timelineIntervals.get(0);
		assertEquals(1,timelineInterval.getCommitCount());
		assertEquals(1000,timelineInterval.getStart());
		assertEquals(1000,timelineInterval.getEnd());
	}
	
	@Test
	public void testGetTimeLineSectionTwoElements() {
		List<Commit> commitList = new ArrayList<>();
		Commit commit = new Commit();
		commit.setMessage("commit");
		commit.setCommiter(user1);
		commit.setDate(new Date(1000));
		commitList.add(commit);
		
		Commit commit2 = new Commit();
		commit2.setMessage("commit");
		commit2.setCommiter(user1);
		commit2.setDate(new Date(2000));
		commitList.add(commit2);
		
		
		gitRepositoryConnector = mock(GitRepositoryConnector.class);
		when(gitRepositoryConnector.findCommits(repository, 100)).thenReturn(commitList);
		statisticsCalculator.setRepositoryConnector(gitRepositoryConnector);
		
		Timeline timeline = statisticsCalculator.getTimeLine(repository, 10);
		
		assertNotNull(timeline);
		List<TimelineInterval> timelineIntervals = timeline.getTimelineIntervals();
		assertNotNull(timelineIntervals);
		
		assertEquals(2,timelineIntervals.size());
		TimelineInterval timelineInterval = timelineIntervals.get(0);
		assertEquals(1,timelineInterval.getCommitCount());
		assertEquals(1000,timelineInterval.getStart());
		assertEquals(1100,timelineInterval.getEnd());
		

		TimelineInterval timelineInterval2 = timelineIntervals.get(1);
		assertEquals(1,timelineInterval2.getCommitCount());
		assertEquals(1909,timelineInterval2.getStart());
		assertEquals(2009,timelineInterval2.getEnd());
	}
	
	
	@Test
	public void testGetTimeLineSectionLoad() {
		List<Commit> manyCommits = new ArrayList<>();
		
		for(int i = 0;i<1000; i++){
			Commit commit = new Commit();
			commit.setMessage("commit");
			commit.setCommiter(user1);
			commit.setDate(new Date(i));
			manyCommits.add(commit);
		}

		
		gitRepositoryConnector = mock(GitRepositoryConnector.class);
		when(gitRepositoryConnector.findCommits(repository, 100)).thenReturn(manyCommits);
		statisticsCalculator.setRepositoryConnector(gitRepositoryConnector);
		
		Timeline timeline = statisticsCalculator.getTimeLine(repository, 10);
		
		assertNotNull(timeline);
		List<TimelineInterval> timelineIntervals = timeline.getTimelineIntervals();
		assertNotNull(timelineIntervals);
		
		assertEquals(10,timelineIntervals.size());
		for(int i = 0;i<10;i++){
			TimelineInterval timelineInterval = timelineIntervals.get(i);
			assertEquals(100,timelineInterval.getCommitCount());
			assertEquals(100*i,timelineInterval.getStart());
			assertEquals(100*(i+1)-1,timelineInterval.getEnd());
		}
	}

}
