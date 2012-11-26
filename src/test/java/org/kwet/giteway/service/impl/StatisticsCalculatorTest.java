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
import org.kwet.giteway.model.TimelineData;
import org.kwet.giteway.model.User;
import org.kwet.giteway.service.StatisticsCalculator;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
public class StatisticsCalculatorTest {

	private static List<Commit> commits;

	private static User user1;

	private static User user2;
	
	private static StatisticsCalculator statisticsCalculator = new StatisticsCalculatorImpl();
	
	@BeforeClass
	public static void beforeClass() {

		user1 = new User();
		user1.setId(1L);
		user1.setLogin("couettos");

		user2 = new User();
		user2.setId(2L);
		user2.setLogin("pipin");

		Commit commit1 = new Commit();
		commit1.setMessage("commit1");
		commit1.setCommitter(user1);
		commit1.setDate(new Date(0));

		Commit commit2 = new Commit();
		commit2.setMessage("commit2");
		commit2.setCommitter(user1);
		commit2.setDate(new Date(5));

		Commit commit3 = new Commit();
		commit3.setMessage("commit3");
		commit3.setCommitter(user1);
		commit3.setDate(new Date(21));

		Commit commit4 = new Commit();
		commit4.setMessage("commit4");
		commit4.setCommitter(user2);
		commit4.setDate(new Date(100));

		commits = new ArrayList<>();
		commits.add(commit1);
		commits.add(commit2);
		commits.add(commit3);
		commits.add(commit4);

	}

	@Test
	public void testCalculateActivity() {
		
		List<CommitterActivity> committerActivity = statisticsCalculator.calculateActivity(commits);
		
		assertNotNull(committerActivity);
		assertEquals(2, committerActivity.size());
		assertEquals(user2.getLogin(), committerActivity.get(0).getLogin());
		assertEquals(25, committerActivity.get(0).getPercentage());
		assertEquals(user1.getLogin(), committerActivity.get(1).getLogin());
		assertEquals(75, committerActivity.get(1).getPercentage());
	}
	
	@Test
	public void testGetTimeLine(){
		
		List<TimelineData> timeLine = statisticsCalculator.getTimeLine(commits,10);
		
		assertNotNull(timeLine);
		assertEquals(10, timeLine.size());
		assertEquals(2, timeLine.get(0).getCommits());
		assertEquals(0L, timeLine.get(0).getTimestamp());
		assertEquals(0L, timeLine.get(1).getCommits());
		assertEquals(10L, timeLine.get(1).getTimestamp());
		assertEquals(1L, timeLine.get(2).getCommits());
		assertEquals(20L, timeLine.get(2).getTimestamp());
		assertEquals(0L, timeLine.get(3).getCommits());
		assertEquals(30L, timeLine.get(3).getTimestamp());
		assertEquals(0L, timeLine.get(4).getCommits());
		assertEquals(40L, timeLine.get(4).getTimestamp());
		assertEquals(0L, timeLine.get(5).getCommits());
		assertEquals(50L, timeLine.get(5).getTimestamp());
		assertEquals(0L, timeLine.get(6).getCommits());
		assertEquals(60L, timeLine.get(6).getTimestamp());
		assertEquals(0L, timeLine.get(7).getCommits());
		assertEquals(70L, timeLine.get(7).getTimestamp());
		assertEquals(0L, timeLine.get(8).getCommits());
		assertEquals(80L, timeLine.get(8).getTimestamp());
		assertEquals(1L, timeLine.get(9).getCommits());
		assertEquals(90L, timeLine.get(9).getTimestamp());
	}

}
