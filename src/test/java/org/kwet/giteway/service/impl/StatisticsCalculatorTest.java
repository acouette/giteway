package org.kwet.giteway.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.kwet.giteway.model.Commit;
import org.kwet.giteway.model.CommitterActivity;
import org.kwet.giteway.model.TimelineChunk;
import org.kwet.giteway.model.User;
import org.kwet.giteway.service.StatisticsCalculator;

public class StatisticsCalculatorTest {

	private List<Commit> baseCommitList;

	private static StatisticsCalculator statisticsCalculator = new StatisticsCalculatorImpl();

	private User user1;

	private User user2;

	@Before
	public void beforeClass() {

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

		baseCommitList = new ArrayList<>();
		baseCommitList.add(commit1);
		baseCommitList.add(commit2);
		baseCommitList.add(commit3);
		baseCommitList.add(commit4);

	}

	@Test
	public void testCalculateActivity() {

		List<CommitterActivity> committerActivity = statisticsCalculator.calculateActivity(baseCommitList);

		assertNotNull(committerActivity);
		assertEquals(2, committerActivity.size());
		assertEquals(user1, committerActivity.get(0).getCommitter());
		assertEquals(75, committerActivity.get(0).getPercentage());
		assertEquals(user2, committerActivity.get(1).getCommitter());
		assertEquals(25, committerActivity.get(1).getPercentage());
	}
	
	@Test
	public void testCalculateActivityOneCommit() {

		List<Commit> commits = new ArrayList<>();
		
		Commit commit = new Commit();
		commit.setMessage("commit");
		commit.setCommiter(user2);
		commit.setDate(new Date(100));
		commits.add(commit);
		
		List<CommitterActivity> committerActivity = statisticsCalculator.calculateActivity(commits);

		assertNotNull(committerActivity);
		assertEquals(1, committerActivity.size());
		assertEquals(user2, committerActivity.get(0).getCommitter());
		assertEquals(100, committerActivity.get(0).getPercentage());
	}

	@Test
	public void testGetTimeLine100ms() {

		List<TimelineChunk> timeLine = statisticsCalculator.getTimeLine(baseCommitList, 10);

		assertNotNull(timeLine);
		assertEquals(3, timeLine.size());
		
		assertEquals(2, timeLine.get(0).getCommitCount());
		assertEquals(0L, timeLine.get(0).getStart());
		assertEquals(9L, timeLine.get(0).getEnd());
		
		assertEquals(1L, timeLine.get(1).getCommitCount());
		assertEquals(20L, timeLine.get(1).getStart());
		assertEquals(29L, timeLine.get(1).getEnd());
		
		assertEquals(1L, timeLine.get(2).getCommitCount());
		assertEquals(90L, timeLine.get(2).getStart());
		assertEquals(99L, timeLine.get(2).getEnd());
	}
	
	@Test
	public void testGetTimeLine101ms() {

		List<Commit> commits = new ArrayList<>();
		commits.addAll(baseCommitList);
		
		Commit commit5 = new Commit();
		commit5.setMessage("commit4");
		commit5.setCommiter(user2);
		commit5.setDate(new Date(100));
		commits.add(commit5);
		
		List<TimelineChunk> timeLine = statisticsCalculator.getTimeLine(commits, 10);

		assertNotNull(timeLine);
		assertEquals(3, timeLine.size());
		
		assertEquals(2, timeLine.get(0).getCommitCount());
		assertEquals(0L, timeLine.get(0).getStart());
		assertEquals(10L, timeLine.get(0).getEnd());
		
		assertEquals(1L, timeLine.get(1).getCommitCount());
		assertEquals(11L, timeLine.get(1).getStart());
		assertEquals(21L, timeLine.get(1).getEnd());
		
		assertEquals(2L, timeLine.get(2).getCommitCount());
		assertEquals(99L, timeLine.get(2).getStart());
		assertEquals(109L, timeLine.get(2).getEnd());
	}
	
	@Test
	public void testGetTimeLineSectionOneElement() {
		List<Commit> commits = new ArrayList<>();
		Commit commit = new Commit();
		commit.setMessage("commit");
		commit.setCommiter(user1);
		commit.setDate(new Date(1000));
		commits.add(commit);
		
		List<TimelineChunk> timeLine = statisticsCalculator.getTimeLine(commits, 10);
		assertNotNull(timeLine);
		assertEquals(1,timeLine.size());
		TimelineChunk timelineChunk = timeLine.get(0);
		assertEquals(1,timelineChunk.getCommitCount());
		assertEquals(1000,timelineChunk.getStart());
		assertEquals(1000,timelineChunk.getEnd());
	}
	
	@Test
	public void testGetTimeLineSectionTwoElements() {
		List<Commit> subCommits = new ArrayList<>();
		Commit commit = new Commit();
		commit.setMessage("commit");
		commit.setCommiter(user1);
		commit.setDate(new Date(1000));
		subCommits.add(commit);
		
		Commit commit2 = new Commit();
		commit2.setMessage("commit");
		commit2.setCommiter(user1);
		commit2.setDate(new Date(2000));
		subCommits.add(commit2);
		
		List<TimelineChunk> timeLine = statisticsCalculator.getTimeLine(subCommits, 10);
		assertNotNull(timeLine);
		assertEquals(2,timeLine.size());
		TimelineChunk timelineChunk = timeLine.get(0);
		assertEquals(1,timelineChunk.getCommitCount());
		assertEquals(1000,timelineChunk.getStart());
		assertEquals(1100,timelineChunk.getEnd());
		

		TimelineChunk timelineChunk2 = timeLine.get(1);
		assertEquals(1,timelineChunk2.getCommitCount());
		assertEquals(1909,timelineChunk2.getStart());
		assertEquals(2009,timelineChunk2.getEnd());
	}
	
	
	@Test
	public void testGetTimeLineSectionLoad() {
		List<Commit> subCommits = new ArrayList<>();
		
		for(int i = 0;i<1000; i++){
			Commit commit = new Commit();
			commit.setMessage("commit");
			commit.setCommiter(user1);
			commit.setDate(new Date(i));
			subCommits.add(commit);
		}
		
		List<TimelineChunk> timeLine = statisticsCalculator.getTimeLine(subCommits, 10);
		assertNotNull(timeLine);
		assertEquals(10,timeLine.size());
		for(int i = 0;i<10;i++){
			TimelineChunk timelineChunk = timeLine.get(i);
			assertEquals(100,timelineChunk.getCommitCount());
			assertEquals(100*i,timelineChunk.getStart());
			assertEquals(100*(i+1)-1,timelineChunk.getEnd());
		}
	}

}
