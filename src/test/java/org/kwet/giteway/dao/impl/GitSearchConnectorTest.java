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
		List<Repository> repositorySearchs = gitSearchConnector.searchRepositoryByKeyword(keyword);

		assertNotNull(repositorySearchs);
		assertEquals(1, repositorySearchs.size());
		Repository repo0 = repositorySearchs.get(0);
		assertEquals("playframework-elasticsearch", repo0.getName());
		assertEquals("feliperazeek", repo0.getOwner());
		assertEquals("Integrate Elastic Search in a Play! Framework Application.", repo0.getDescription());
	}

	@Test
	public void testSearchRepositoryByKeywordEmpty() throws Exception {

		String keyword = "notMatchingKeyword";
		String responseFile = "search-repos-empty";
		configureHttpClient(responseFile);
		List<Repository> repositorySearchs = gitSearchConnector.searchRepositoryByKeyword(keyword);

		assertNotNull(repositorySearchs);
		assertEquals(0, repositorySearchs.size());
	}

	@Test
	public void testSearchRepositoryByKeywordUnexpected() throws Exception {
		try {

			String keyword = "anyKeyword";
			String responseFile = "search-repos-unexpected";
			configureHttpClient(responseFile);
			gitSearchConnector.searchRepositoryByKeyword(keyword);

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


}