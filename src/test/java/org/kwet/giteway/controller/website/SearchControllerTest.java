package org.kwet.giteway.controller.website;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.kwet.giteway.dao.GitSearchConnector;
import org.kwet.giteway.model.Repository;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ExtendedModelMap;

public class SearchControllerTest {

	private GitSearchConnector gitSearchConnector;

	
	@SuppressWarnings("unchecked")
	@Test
	public void testSearchRepository() {

		List<Repository> repositoryList = new ArrayList<>();

		Repository repository1 = new Repository();
		repository1.setName("couettos");
		repository1.setName("giteway");
		repositoryList.add(repository1);

		Repository repository2 = new Repository();
		repository2.setName("pipin");
		repository2.setName("cowork");
		repositoryList.add(repository2);

		gitSearchConnector = mock(GitSearchConnector.class);
		when(gitSearchConnector.searchRepositoryByKeyword("test")).thenReturn(repositoryList);

		SearchController searchController = new SearchController();

		ExtendedModelMap uiModel = new ExtendedModelMap();
		ReflectionTestUtils.setField(searchController, "gitSearchConnector", gitSearchConnector);

		String result = searchController.handleSearch(uiModel, "test");
		Assert.assertEquals("search", result);
		List<Repository> repositoriesInModel = (List<Repository>) uiModel.get("repositories");
		Assert.assertEquals(2, repositoriesInModel.size());
		Assert.assertNull(uiModel.get("noResult"));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSearchRepositoryNoResult() {
		gitSearchConnector = mock(GitSearchConnector.class);
		when(gitSearchConnector.searchRepositoryByKeyword("test")).thenReturn(new ArrayList<Repository>());

		SearchController searchController = new SearchController();
		ReflectionTestUtils.setField(searchController, "gitSearchConnector", gitSearchConnector);

		ExtendedModelMap uiModel = new ExtendedModelMap();
		String result = searchController.handleSearch(uiModel, "test");

		Assert.assertEquals("search", result);
		List<Repository> repositoriesInModel = (List<Repository>) uiModel.get("repositories");
		Assert.assertNull(repositoriesInModel);
		Assert.assertEquals(true, uiModel.get("noResult"));
	}

	@Test
	public void testAutocomplete() throws Exception {
		gitSearchConnector = mock(GitSearchConnector.class);

		List<String> names = new ArrayList<>();
		names.add("foobar");
		names.add("foobob");

		when(gitSearchConnector.searchRepositoryNames("foo", 5)).thenReturn(names);

		SearchController searchController = new SearchController();
		ObjectMapper objectMapper = new ObjectMapper();

		ReflectionTestUtils.setField(searchController, "gitSearchConnector", gitSearchConnector);
		ReflectionTestUtils.setField(searchController, "objectMapper", objectMapper);

		ExtendedModelMap uiModel = new ExtendedModelMap();
		String result = searchController.handleAutosuggest(uiModel, "foo");
		Assert.assertEquals("[\"foobar\",\"foobob\"]", result);

	}

}
