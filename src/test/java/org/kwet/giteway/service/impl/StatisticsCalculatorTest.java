package org.kwet.giteway.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.kwet.giteway.model.Commit;
import org.kwet.giteway.model.CommitterActivity;
import org.kwet.giteway.model.TimelineChunk;
import org.kwet.giteway.service.StatisticsCalculator;

public class StatisticsCalculatorTest {

	private static List<Commit> commits;

	private static StatisticsCalculator statisticsCalculator = new StatisticsCalculatorImpl();

	static String login1 = "couettos";
	static String login2 = "pipin";
	
	@BeforeClass
	public static void beforeClass() {

		Commit commit1 = new Commit();
		commit1.setMessage("commit1");
		commit1.setLogin(login1);
		commit1.setDate(new Date(0));

		Commit commit2 = new Commit();
		commit2.setMessage("commit2");
		commit2.setLogin(login1);
		commit2.setDate(new Date(5));

		Commit commit3 = new Commit();
		commit3.setMessage("commit3");
		commit3.setLogin(login1);
		commit3.setDate(new Date(21));

		Commit commit4 = new Commit();
		commit4.setMessage("commit4");
		commit4.setLogin(login2);
		commit4.setDate(new Date(100));

		commits = new ArrayList<>();
		commits.add(commit1);
		commits.add(commit2);
		commits.add(commit3);
		commits.add(commit4);

	}

	@Test
	public void testCalculateActivity() {

		System.out.println(commits);
		
		List<CommitterActivity> committerActivity = statisticsCalculator.calculateActivity(commits);

		assertNotNull(committerActivity);
		assertEquals(2, committerActivity.size());
		assertEquals(login2, committerActivity.get(0).getLogin());
		assertEquals(25, committerActivity.get(0).getPercentage());
		assertEquals(login1, committerActivity.get(1).getLogin());
		assertEquals(75, committerActivity.get(1).getPercentage());
	}

	@Test
	public void testGetTimeLine() {

		List<TimelineChunk> timeLine = statisticsCalculator.getTimeLine(commits, 10);

		assertNotNull(timeLine);
		assertEquals(10, timeLine.size());
		assertEquals(2, timeLine.get(0).getCommitCount());
		assertEquals(0L, timeLine.get(0).getStart());
		assertEquals(9L, timeLine.get(0).getEnd());
		assertEquals(0L, timeLine.get(1).getCommitCount());
		assertEquals(10L, timeLine.get(1).getStart());
		assertEquals(19L, timeLine.get(1).getEnd());
		assertEquals(1L, timeLine.get(2).getCommitCount());
		assertEquals(20L, timeLine.get(2).getStart());
		assertEquals(29L, timeLine.get(2).getEnd());
		assertEquals(0L, timeLine.get(3).getCommitCount());
		assertEquals(30L, timeLine.get(3).getStart());
		assertEquals(39L, timeLine.get(3).getEnd());
		assertEquals(0L, timeLine.get(4).getCommitCount());
		assertEquals(40L, timeLine.get(4).getStart());
		assertEquals(49L, timeLine.get(4).getEnd());
		assertEquals(0L, timeLine.get(5).getCommitCount());
		assertEquals(50L, timeLine.get(5).getStart());
		assertEquals(59L, timeLine.get(5).getEnd());
		assertEquals(0L, timeLine.get(6).getCommitCount());
		assertEquals(60L, timeLine.get(6).getStart());
		assertEquals(69L, timeLine.get(6).getEnd());
		assertEquals(0L, timeLine.get(7).getCommitCount());
		assertEquals(70L, timeLine.get(7).getStart());
		assertEquals(79L, timeLine.get(7).getEnd());
		assertEquals(0L, timeLine.get(8).getCommitCount());
		assertEquals(80L, timeLine.get(8).getStart());
		assertEquals(89L, timeLine.get(8).getEnd());
		assertEquals(1L, timeLine.get(9).getCommitCount());
		assertEquals(90L, timeLine.get(9).getStart());
		assertEquals(100L, timeLine.get(9).getEnd());
	}

}
