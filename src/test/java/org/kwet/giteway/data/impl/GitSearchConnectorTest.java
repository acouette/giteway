package org.kwet.giteway.data.impl;

import java.util.List;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.kwet.giteway.data.GitSearchConnector;
import org.kwet.giteway.model.RepositorySearch;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.client.RestTemplate;

public class GitSearchConnectorTest extends BaseGitConnectorTest {

	private GitSearchConnector gitSearchConnector;

	@Before
	public void before() {
		restTemplate = new RestTemplate();
		gitSearchConnector = new GitSearchConnectorImpl(restTemplate);
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

		} catch (HttpMessageNotReadableException e) {
		}
	}

	private List<RepositorySearch> searchRepositoryByKeyword(String keyword, String responseFile) {

		String url = GitSearchConnectorImpl.GET_REPOSITORIES_BY_KEYWORD.replace("{keyword}", keyword);
		configureRestTemplateMock(url, responseFile);
		return gitSearchConnector.searchRepositoryByKeyword(keyword);
	}

}