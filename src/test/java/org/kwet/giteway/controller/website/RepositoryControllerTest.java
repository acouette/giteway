package org.kwet.giteway.controller.website;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.kwet.giteway.dao.GitRepositoryConnector;
import org.kwet.giteway.model.Repository;
import org.kwet.giteway.model.User;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ExtendedModelMap;

public class RepositoryControllerTest {

	private GitRepositoryConnector gitRepositoryConnector;


	@Test
	public void testGetRepositoryData() throws IOException {
		gitRepositoryConnector = mock(GitRepositoryConnector.class);

		String owner = "Couettos";
		String name = "giteway";
		String desc = "The most amazing webapp ever";

		Repository repository = new Repository();
		repository.setOwner(owner);
		repository.setName(name);
		repository.setDescription(desc);

		when(gitRepositoryConnector.find(owner, name)).thenReturn(repository);

		List<User> userList = new ArrayList<>();
		User user1 = new User();
		user1.setLogin("Couettos");
		userList.add(user1);

		User user2 = new User();
		user2.setLogin("pipin");
		userList.add(user2);


		when(gitRepositoryConnector.findCollaborators(repository)).thenReturn(userList);


		RepositoryController repositoryController = new RepositoryController();
		ReflectionTestUtils.setField(repositoryController, "gitRepositoryConnector", gitRepositoryConnector);

		ExtendedModelMap uiModel = new ExtendedModelMap();
		String result = repositoryController.getRepositoryStats(uiModel, owner, name);

		Assert.assertEquals("repository", result);

		Assert.assertEquals(repository, uiModel.get("repository"));

		Assert.assertEquals(userList, uiModel.get("collaborators"));

	}
	
	@Test
	public void testGetRepositoryUnknown() throws IOException {
		gitRepositoryConnector = mock(GitRepositoryConnector.class);

		String owner = "Couettos";
		String name = "giteway";

		when(gitRepositoryConnector.find(owner, name)).thenReturn(null);

		List<User> userList = new ArrayList<>();
		User user1 = new User();
		user1.setLogin("Couettos");
		userList.add(user1);

		User user2 = new User();
		user2.setLogin("pipin");
		userList.add(user2);



		RepositoryController repositoryController = new RepositoryController();
		ReflectionTestUtils.setField(repositoryController, "gitRepositoryConnector", gitRepositoryConnector);


		ExtendedModelMap uiModel = new ExtendedModelMap();
		String result = repositoryController.getRepositoryStats(uiModel, owner, name);

		Assert.assertEquals("search", result);
		
		Assert.assertEquals(true, uiModel.get("unknowRepo"));

	}

}
