package org.kwet.giteway.controller.website;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.kwet.giteway.dao.GitSearchConnector;
import org.kwet.giteway.model.Repository;
import org.kwet.giteway.model.SearchType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ExtendedModelMap;

public class SearchControllerTest {

	private GitSearchConnector gitSearchConnector;

	
	@SuppressWarnings("unchecked")
	@Test
	public void testSearchRepositoryByKeyword() throws IOException {

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
		when(gitSearchConnector.searchRepositoriesByKeyword("test")).thenReturn(repositoryList);

		SearchController searchController = new SearchController();

		ExtendedModelMap uiModel = new ExtendedModelMap();
		ReflectionTestUtils.setField(searchController, "gitSearchConnector", gitSearchConnector);

		String result = searchController.handleSearch(uiModel, SearchType.KEYWORD.getType(),"test");
		Assert.assertEquals("search", result);
		List<Repository> repositoriesInModel = (List<Repository>) uiModel.get("repositories");
		Assert.assertEquals(2, repositoriesInModel.size());
		Assert.assertNull(uiModel.get("noResult"));
		Assert.assertNull((Boolean)uiModel.get("extraReposAvailable"));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSearchRepositoryByOwner() throws IOException {

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
		when(gitSearchConnector.searchRepositoriesByOwner("test")).thenReturn(repositoryList);

		SearchController searchController = new SearchController();

		ExtendedModelMap uiModel = new ExtendedModelMap();
		ReflectionTestUtils.setField(searchController, "gitSearchConnector", gitSearchConnector);

		String result = searchController.handleSearch(uiModel, SearchType.OWNER.getType(),"test");
		Assert.assertEquals("search", result);
		List<Repository> repositoriesInModel = (List<Repository>) uiModel.get("repositories");
		Assert.assertEquals(2, repositoriesInModel.size());
		Assert.assertNull(uiModel.get("noResult"));
		Assert.assertNull((Boolean)uiModel.get("extraReposAvailable"));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSearchRepositoryNoResult() throws IOException {
		gitSearchConnector = mock(GitSearchConnector.class);
		when(gitSearchConnector.searchRepositoriesByKeyword("test")).thenReturn(new ArrayList<Repository>());

		SearchController searchController = new SearchController();
		ReflectionTestUtils.setField(searchController, "gitSearchConnector", gitSearchConnector);

		ExtendedModelMap uiModel = new ExtendedModelMap();
		String result = searchController.handleSearch(uiModel, SearchType.KEYWORD.getType(),"test");

		Assert.assertEquals("search", result);
		List<Repository> repositoriesInModel = (List<Repository>) uiModel.get("repositories");
		Assert.assertNull(repositoriesInModel);
		Assert.assertEquals(true, uiModel.get("noResult"));
		Assert.assertNull(uiModel.get("extraReposAvailable"));
	}

	@Test
	public void testAutocompleteByKeyword() throws Exception {
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
		String result = searchController.handleAutosuggest(uiModel,  SearchType.KEYWORD.getType(), "foo");
		Assert.assertEquals("[\"foobar\",\"foobob\"]", result);

	}
	
	@Test
	public void testAutocompleteByOwner() throws Exception {
		gitSearchConnector = mock(GitSearchConnector.class);

		List<String> names = new ArrayList<>();
		names.add("foobar");
		names.add("foobob");

		when(gitSearchConnector.searchUserNames("foo", 5)).thenReturn(names);

		SearchController searchController = new SearchController();
		ObjectMapper objectMapper = new ObjectMapper();

		ReflectionTestUtils.setField(searchController, "gitSearchConnector", gitSearchConnector);
		ReflectionTestUtils.setField(searchController, "objectMapper", objectMapper);

		ExtendedModelMap uiModel = new ExtendedModelMap();
		String result = searchController.handleAutosuggest(uiModel,  SearchType.OWNER.getType(), "foo");
		Assert.assertEquals("[\"foobar\",\"foobob\"]", result);

	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSearchRepositoryLimit() throws IOException {

		List<Repository> repositoryList = new ArrayList<>();

		for(int i = 0;i<12;i++){
			Repository repository = new Repository();
			repository.setName("couettos");
			repository.setName("giteway");
			repositoryList.add(repository);
		}

		gitSearchConnector = mock(GitSearchConnector.class);
		when(gitSearchConnector.searchRepositoriesByKeyword("test")).thenReturn(repositoryList);

		SearchController searchController = new SearchController();

		ExtendedModelMap uiModel = new ExtendedModelMap();
		ReflectionTestUtils.setField(searchController, "gitSearchConnector", gitSearchConnector);

		String result = searchController.handleSearch(uiModel,SearchType.KEYWORD.getType(), "test");
		Assert.assertEquals("search", result);
		List<Repository> repositoriesInModel = (List<Repository>) uiModel.get("repositories");
		Assert.assertEquals(10, repositoriesInModel.size());
		Assert.assertTrue((Boolean)uiModel.get("extraReposAvailable"));
		Assert.assertNull(uiModel.get("noResult"));
	}
	
	@Test
	public void testExtraReposSearchByKeyword() throws Exception {
		List<Repository> repositoryList = new ArrayList<>();

		for(int i = 0;i<12;i++){
			Repository repository = new Repository();
			repository.setOwner("couettos");
			repository.setName("giteway"+i);
			repository.setDescription("coolRepo"+i);
			repositoryList.add(repository);
		}

		gitSearchConnector = mock(GitSearchConnector.class);
		when(gitSearchConnector.searchRepositoriesByKeyword("test")).thenReturn(repositoryList);

		SearchController searchController = new SearchController();

		ExtendedModelMap uiModel = new ExtendedModelMap();
		ReflectionTestUtils.setField(searchController, "gitSearchConnector", gitSearchConnector);
		ReflectionTestUtils.setField(searchController, "objectMapper", new ObjectMapper());
		
		String result = searchController.handleExtraSearch(uiModel, SearchType.KEYWORD.getType(), "test");
		System.out.println(result);
		Assert.assertEquals("[{\"name\":\"giteway10\",\"owner\":\"couettos\",\"description\":\"coolRepo10\"},{\"name\":\"giteway11\",\"owner\":\"couettos\",\"description\":\"coolRepo11\"}]", result);
	}
	
	@Test
	public void testExtraReposSearchByOwner() throws Exception {
		List<Repository> repositoryList = new ArrayList<>();

		for(int i = 0;i<12;i++){
			Repository repository = new Repository();
			repository.setOwner("couettos");
			repository.setName("giteway"+i);
			repository.setDescription("coolRepo"+i);
			repositoryList.add(repository);
		}

		gitSearchConnector = mock(GitSearchConnector.class);
		when(gitSearchConnector.searchRepositoriesByOwner("test")).thenReturn(repositoryList);

		SearchController searchController = new SearchController();

		ExtendedModelMap uiModel = new ExtendedModelMap();
		ReflectionTestUtils.setField(searchController, "gitSearchConnector", gitSearchConnector);
		ReflectionTestUtils.setField(searchController, "objectMapper", new ObjectMapper());
		
		String result = searchController.handleExtraSearch(uiModel, SearchType.OWNER.getType(), "test");
		System.out.println(result);
		Assert.assertEquals("[{\"name\":\"giteway10\",\"owner\":\"couettos\",\"description\":\"coolRepo10\"},{\"name\":\"giteway11\",\"owner\":\"couettos\",\"description\":\"coolRepo11\"}]", result);
	}

}
