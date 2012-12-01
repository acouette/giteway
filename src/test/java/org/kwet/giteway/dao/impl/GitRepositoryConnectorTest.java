package org.kwet.giteway.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kwet.giteway.dao.GitRepositoryConnector;
import org.kwet.giteway.model.Commit;
import org.kwet.giteway.model.Repository;
import org.kwet.giteway.model.User;
import org.springframework.test.util.ReflectionTestUtils;

public class GitRepositoryConnectorTest extends BaseGitConnectorTest {

	private static Repository repository;

	private GitRepositoryConnector gitRepositoryConnector;

	@BeforeClass
	public static void beforeClass() {
		repository = new Repository();
		repository.setName("play!");
		repository.setOwner("Zenexity");
	}

	@Before
	public void before() {
		gitRepositoryConnector = new GitRepositoryConnectorImpl();
		ReflectionTestUtils.setField(gitRepositoryConnector, "gitHttpClient",gitHttpClient);
	}

	@Test
	public void testFind() throws IllegalStateException, IOException {
		String name = "Hello-World";
		String owner = "octocat";
		String responseFile = "repo";

		configureHttpClient(responseFile);

		Repository repository = gitRepositoryConnector.find(owner, name);
		assertNotNull(repository);

		assertEquals("octocat", repository.getOwner());

	}

	@Test
	public void testFindCollaborators() throws Exception {

		String responseFile = "repo-collaborators";
		List<User> collaborators = findCollaborators(responseFile);

		assertNotNull(collaborators);
		assertEquals(2, collaborators.size());
		User collaborator = collaborators.get(0);
		assertEquals("cbeams", collaborator.getLogin());
		assertEquals(
				"https://secure.gravatar.com/avatar/29490cd51d5a93b61cc946844f471589?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png",
				collaborator.getAvatarUrl());
	}

	private List<User> findCollaborators(String responseFile) throws IllegalStateException, IOException {
		configureHttpClient(responseFile);
		return gitRepositoryConnector.findCollaborators(repository.getOwner(),repository.getName());
	}

	@Test
	public void testFindCommits() throws Exception {

		String responseFile = "repo-commits";
		List<Commit> commits = findCommits(responseFile);

		assertNotNull(commits);
		assertEquals(2, commits.size());
		Commit commit = commits.get(0);
		assertEquals("Set the ApplicationContext prop of ExceptionResolver...", commit.getMessage());
		assertEquals("rstoyanchev", commit.getCommiter().getLogin());
		assertEquals(1353701572000L, commit.getDate().getTime());

	}

	private List<Commit> findCommits(String responseFile) throws IllegalStateException, IOException {

		configureHttpClient(responseFile);
		return gitRepositoryConnector.findCommits(repository.getOwner(),repository.getName(),100);
	}

}