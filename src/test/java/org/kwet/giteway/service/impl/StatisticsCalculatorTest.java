package org.kwet.giteway.service.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kwet.giteway.data.GitRepositoryConnector;
import org.kwet.giteway.model.Commit;
import org.kwet.giteway.model.Repository;
import org.kwet.giteway.model.User;
import org.kwet.giteway.service.StatisticsCalculator;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
public class StatisticsCalculatorTest {

	private static List<Commit> commits;

	private static User user1;

	private static User user2;

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

		Commit commit2 = new Commit();
		commit2.setMessage("commit2");
		commit2.setCommitter(user1);

		Commit commit3 = new Commit();
		commit3.setMessage("commit3");
		commit3.setCommitter(user1);

		Commit commit4 = new Commit();
		commit4.setMessage("commit4");
		commit4.setCommitter(user2);

		commits = new ArrayList<>();
		commits.add(commit1);
		commits.add(commit2);
		commits.add(commit3);
		commits.add(commit4);

	}

	@Test
	public void testCalculateActivity() {

		Repository repository = new Repository();

		GitRepositoryConnector repositoryConnector = mock(GitRepositoryConnector.class);
		StatisticsCalculator statisticsCalculator = new StatisticsCalculatorImpl(repositoryConnector);
		when(repositoryConnector.findCommits(repository)).thenReturn(commits);

		Map<User, Double> commitPercentage = statisticsCalculator.calculateActivity(repository);
		assertNotNull(commitPercentage);

		assertEquals(2, commitPercentage.size());

		Double result = commitPercentage.get(user1);
		assertEquals(new Double(0.75), result);

		Double result2 = commitPercentage.get(user2);
		assertEquals(new Double(0.25), result2);

	}

}
