package org.kwet.giteway.github.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.kwet.giteway.exception.GithubRequestException;
import org.kwet.giteway.github.GitSearchConnector;
import org.kwet.giteway.model.RepositorySearch;

public class GitSearchConnectorTest extends BaseGitConnectorTest {

	private GitSearchConnector gitSearchConnector;

	@Before
	public void before() {
		gitSearchConnector = new GitSearchConnectorImpl();
		((GitSearchConnectorImpl)gitSearchConnector).setGitHttpClient(gitHttpClient);
	}

	@Test
	public void testSearchRepositoryByKeyword() throws Exception {

		String keyword = "matchingKeyword";
		String responseFile = "search-repos";
		List<RepositorySearch> repositorySearchs = searchRepositoryByKeyword(keyword, responseFile);

		assertNotNull(repositorySearchs);
		assertEquals(1, repositorySearchs.size());
		RepositorySearch repo0 = repositorySearchs.get(0);
		assertEquals("playframework-elasticsearch", repo0.getName());
		assertEquals("feliperazeek", repo0.getUsername());
		assertEquals("Integrate Elastic Search in a Play! Framework Application.", repo0.getDescription());
	}

	@Test
	public void testSearchRepositoryByKeywordEmpty() throws Exception {

		String keyword = "notMatchingKeyword";
		String responseFile = "search-repos-empty";
		List<RepositorySearch> repositorySearchs = searchRepositoryByKeyword(keyword, responseFile);

		assertNotNull(repositorySearchs);
		assertEquals(0, repositorySearchs.size());
	}

	@Test
	public void testSearchRepositoryByKeywordUnexpected() throws Exception {
		try {

			String keyword = "anyKeyword";
			String responseFile = "search-repos-unexpected";
			searchRepositoryByKeyword(keyword, responseFile);

			fail("expected HttpMessageNotReadableException");

		} catch (GithubRequestException e) {
		}
	}

	private List<RepositorySearch> searchRepositoryByKeyword(String keyword, String responseFile) throws IllegalStateException, IOException {

		String url = GitSearchConnectorImpl.GET_REPOSITORIES_BY_KEYWORD.replace("{keyword}", keyword);
		configureHttpClient(url, responseFile);
		return gitSearchConnector.searchRepositoryByKeyword(keyword);
	}

}