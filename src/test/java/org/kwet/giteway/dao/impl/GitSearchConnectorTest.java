package org.kwet.giteway.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.kwet.giteway.dao.GitSearchConnector;
import org.kwet.giteway.model.GitewayRequestException;
import org.kwet.giteway.model.Repository;
import org.springframework.test.util.ReflectionTestUtils;

public class GitSearchConnectorTest extends BaseGitConnectorTest {

	private GitSearchConnector gitSearchConnector;

	@Before
	public void before() {
		gitSearchConnector = new GitSearchConnectorImpl();
		ReflectionTestUtils.setField(gitSearchConnector, "gitHttpClient",gitHttpClient);
	}

	@Test
	public void testSearchRepositoryByKeyword() throws Exception {

		String keyword = "matchingKeyword";
		String responseFile = "search-repos";
		configureHttpClient(responseFile);
		List<Repository> repositorySearchs = gitSearchConnector.searchRepositoriesByKeyword(keyword);

		assertNotNull(repositorySearchs);
		assertEquals(1, repositorySearchs.size());
		Repository repo0 = repositorySearchs.get(0);
		assertEquals("playframework-elasticsearch", repo0.getName());
		assertEquals("feliperazeek", repo0.getOwner());
		assertEquals("Integrate Elastic Search in a Play! Framework Application.", repo0.getDescription());
	}
	
	@Test
	public void testSearchRepositoryByOwner() throws Exception {

		String keyword = "guillaumebort";
		String responseFile = "search-repos-by-owner";
		configureHttpClient(responseFile);
		List<Repository> repositorySearchs = gitSearchConnector.searchRepositoriesByOwner(keyword);

		assertNotNull(repositorySearchs);
		assertEquals(2, repositorySearchs.size());
		Repository repo0 = repositorySearchs.get(0);
		assertEquals("bootstrap", repo0.getName());
		assertEquals("guillaumebort", repo0.getOwner());
		assertEquals("Sleek, intuitive, and powerful front-end framework for faster and easier web development.", repo0.getDescription());
		Repository repo1 = repositorySearchs.get(1);
		assertEquals("cagette", repo1.getName());
		assertEquals("guillaumebort", repo1.getOwner());
		assertEquals("The quick and dirty datastore for prototyping", repo1.getDescription());
	}
	
	@Test
	public void testSearchRepositoryByUnknowOwner() throws Exception {

		String keyword = "unknownOwner";
		String responseFile = "search-repos-by-owner";
		configureHttpClient(responseFile,404);
		List<Repository> repositorySearchs = gitSearchConnector.searchRepositoriesByOwner(keyword);

		assertNotNull(repositorySearchs);
		assertEquals(0, repositorySearchs.size());
	}

	@Test
	public void testSearchRepositoryByKeywordEmpty() throws Exception {

		String keyword = "notMatchingKeyword";
		String responseFile = "search-repos-empty";
		configureHttpClient(responseFile);
		List<Repository> repositorySearchs = gitSearchConnector.searchRepositoriesByKeyword(keyword);

		assertNotNull(repositorySearchs);
		assertEquals(0, repositorySearchs.size());
	}

	@Test
	public void testSearchRepositoryByKeywordUnexpected() throws Exception {
		try {

			String keyword = "anyKeyword";
			String responseFile = "search-repos-unexpected";
			configureHttpClient(responseFile);
			gitSearchConnector.searchRepositoriesByKeyword(keyword);

			fail("expected HttpMessageNotReadableException");

		} catch (GitewayRequestException e) {
		}
	}
	
	@Test
	public void testSearchRepositoryNamesSingle() throws Exception{
		String keyword = "play";
		String responseFile = "search-repos";
		configureHttpClient(responseFile);
		List<String> repositoryNames = gitSearchConnector.searchRepositoryNames(keyword, 5);

		assertNotNull(repositoryNames);
		assertEquals(1, repositoryNames.size());
		assertEquals("playframework-elasticsearch", repositoryNames.get(0));
	}
	
	@Test
	public void testSearchRepositoryNamesMulti() throws Exception{
		String keyword = "play";
		String responseFile = "search-repos-autocomplete";
		configureHttpClient(responseFile);
		List<String> repositoryNames = gitSearchConnector.searchRepositoryNames(keyword, 5);

		assertNotNull(repositoryNames);
		assertEquals(5, repositoryNames.size());
		assertEquals("playframework-1", repositoryNames.get(0));
		assertEquals("playframework-2", repositoryNames.get(1));
		assertEquals("playframework-3", repositoryNames.get(2));
		assertEquals("playframework-4", repositoryNames.get(3));
		assertEquals("playframework-elasticsearch", repositoryNames.get(4));
	}
	
	
	@Test
	public void testSearchUserNamesMulti() throws Exception{
		String keyword = "mar";
		String responseFile = "repo-users-autocomplete";
		configureHttpClient(responseFile);
		List<String> repositoryNames = gitSearchConnector.searchUserNames(keyword, 5);
		assertNotNull(repositoryNames);
		assertEquals(5, repositoryNames.size());
		assertEquals("mar", repositoryNames.get(0));
		assertEquals("marak", repositoryNames.get(1));
		assertEquals("marasb16", repositoryNames.get(2));
		assertEquals("marltu", repositoryNames.get(3));
		assertEquals("martaponzoni", repositoryNames.get(4));
	}


}