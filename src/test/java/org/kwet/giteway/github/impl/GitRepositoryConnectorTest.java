package org.kwet.giteway.github.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kwet.giteway.github.GitRepositoryConnector;
import org.kwet.giteway.model.Commit;
import org.kwet.giteway.model.Repository;
import org.kwet.giteway.model.User;
import org.springframework.web.client.RestTemplate;

public class GitRepositoryConnectorTest extends BaseGitConnectorTest {

	private static Repository repository;

	private GitRepositoryConnector gitRepositoryConnector;

	@BeforeClass
	public static void beforeClass() {
		repository = new Repository();
		repository.setName("play!");
		User user = new User();
		user.setLogin("Zenexity");
		repository.setOwner(user);
	}

	@Before
	public void before() {
		restTemplate = new RestTemplate();
		//gitRepositoryConnector = new GitRepositoryConnectorImpl(restTemplate);
	}

	@Test
	public void testFind() {
		String name = "Hello-World";
		String owner = "octocat";
		String responseFile = "repo";

		String url = GitRepositoryConnectorImpl.GET_REPOSITORY.replace("{owner}", owner).replace("{name}", name);

		configureRestTemplateMock(url, responseFile);

		Repository repository = gitRepositoryConnector.find(owner, name);
		assertNotNull(repository);

		assertEquals("octocat", repository.getOwner().getLogin());

	}

	@Test
	public void testFindCollaborators() throws Exception {

		String responseFile = "repo-collaborators";
		List<User> collaborators = findCollaborators(responseFile);

		assertNotNull(collaborators);
		assertEquals(2, collaborators.size());
		User collaborator = collaborators.get(0);
		assertEquals(301810l, collaborator.getId());
		assertEquals("cbeams", collaborator.getLogin());
		assertEquals(
				"https://secure.gravatar.com/avatar/29490cd51d5a93b61cc946844f471589?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png",
				collaborator.getAvatarUrl());
	}

	private List<User> findCollaborators(String responseFile) {

		String url = GitRepositoryConnectorImpl.GET_COLLABORATORS.replace("{owner}", repository.getOwner().getLogin()).replace("{name}",
				repository.getName());

		configureRestTemplateMock(url, responseFile);
		return gitRepositoryConnector.findCollaborators(repository);
	}

	@Test
	public void testFindCommits() throws Exception {

		String responseFile = "repo-commits";
		List<Commit> commits = findCommits(responseFile);

		assertNotNull(commits);
		assertEquals(2, commits.size());
		Commit commit = commits.get(0);
		assertEquals("Set the ApplicationContext prop of ExceptionResolver...", commit.getMessage());
		assertNotNull(commit.getCommitter());
		assertEquals("rstoyanchev", commit.getCommitter().getLogin());
		assertEquals(401908, commit.getCommitter().getId());
		assertEquals(1353701572000L,commit.getDate().getTime());
		
	}

	private List<Commit> findCommits(String responseFile) {

		String url = GitRepositoryConnectorImpl.GET_COMMITS.replace("{owner}", repository.getOwner().getLogin()).replace("{name}",
				repository.getName());
		configureRestTemplateMock(url, responseFile);
		return gitRepositoryConnector.findCommits(repository);
	}

}