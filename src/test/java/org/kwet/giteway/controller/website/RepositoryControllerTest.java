package org.kwet.giteway.controller.website;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.kwet.giteway.dao.GitRepositoryConnector;
import org.kwet.giteway.model.Commit;
import org.kwet.giteway.model.Repository;
import org.kwet.giteway.model.User;
import org.kwet.giteway.service.impl.StatisticsCalculatorImpl;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ExtendedModelMap;

public class RepositoryControllerTest {

	private GitRepositoryConnector gitRepositoryConnector;

	private List<Repository> repositoryList;

	@Before
	public void before() {

		repositoryList = new ArrayList<>();

		Repository repository1 = new Repository();
		repository1.setName("couettos");
		repository1.setName("giteway");
		repositoryList.add(repository1);

		Repository repository2 = new Repository();
		repository2.setName("pipin");
		repository2.setName("cowork");
		repositoryList.add(repository2);

	}

	@Test
	public void testGetRepositoryStats() throws IOException {
		gitRepositoryConnector = mock(GitRepositoryConnector.class);

		String owner = "Couettos";
		String name = "giteway";

		Repository repository = new Repository();
		repository.setOwner(owner);
		repository.setName(name);
		repository.setDescription("The most amazing webapp ever");

		when(gitRepositoryConnector.find(owner, name)).thenReturn(repository);

		List<User> userList = new ArrayList<>();
		User user1 = new User();
		user1.setLogin("Couettos");
		userList.add(user1);

		User user2 = new User();
		user2.setLogin("pipin");
		userList.add(user2);


		when(gitRepositoryConnector.findCollaborators(repository)).thenReturn(userList);

		Commit commit1 = new Commit();
		commit1.setMessage("commit1");
		commit1.setCommiter(user1);
		commit1.setDate(new Date(0));

		Commit commit2 = new Commit();
		commit2.setMessage("commit2");
		commit2.setCommiter(user2);
		commit2.setDate(new Date(5));

		List<Commit> commitList = new ArrayList<>();
		commitList.add(commit1);
		commitList.add(commit2);

		when(gitRepositoryConnector.findCommits(repository, 100)).thenReturn(commitList);

		RepositoryController repositoryController = new RepositoryController();
		ReflectionTestUtils.setField(repositoryController, "gitRepositoryConnector", gitRepositoryConnector);

		// Here we will not mock the statisticCalculator. It is not a purely
		// unit test
		StatisticsCalculatorImpl statisticsCalculator = new StatisticsCalculatorImpl();
		statisticsCalculator.setRepositoryConnector(gitRepositoryConnector);

		ExtendedModelMap uiModel = new ExtendedModelMap();
		String result = repositoryController.getRepositoryStats(uiModel, owner, name);

		Assert.assertEquals("repository", result);

		Assert.assertEquals(repository, uiModel.get("repository"));

		Assert.assertEquals(userList, uiModel.get("collaborators"));

	}

}
