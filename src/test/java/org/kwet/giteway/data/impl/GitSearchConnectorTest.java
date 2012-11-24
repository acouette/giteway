package org.kwet.giteway.data.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kwet.giteway.model.RepositorySearch;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class) 
public class GitSearchConnectorTest extends BaseGitConnectorTest{
	
	@Test
	public void testSearchRepositoryByKeyword() throws Exception{

		String keyword = "matchingKeyword";
		String responseFile = "search-repos";
		List<RepositorySearch> repositorySearchs = searchRepositoryByKeyword(keyword, responseFile);
		
		Assert.assertNotNull(repositorySearchs);
		Assert.assertEquals(1,repositorySearchs.size());
		RepositorySearch repo0 = repositorySearchs.get(0);
		Assert.assertEquals("playframework-elasticsearch",repo0.getName());
		Assert.assertEquals("feliperazeek",repo0.getUsername());
		Assert.assertEquals("Integrate Elastic Search in a Play! Framework Application.",repo0.getDescription());
	}
	
	@Test
	public void testSearchRepositoryByKeywordEmpty() throws Exception{
		
		String keyword = "notMatchingKeyword";
		String responseFile = "search-repos-empty";
		List<RepositorySearch> repositorySearchs = searchRepositoryByKeyword(keyword, responseFile);
		
		Assert.assertNotNull(repositorySearchs);
		Assert.assertEquals(0,repositorySearchs.size());
	}
	
	@Test
	public void testSearchRepositoryByKeywordUnexpected() throws Exception{
		try{
			
			String keyword = "anyKeyword";
			String responseFile = "search-repos-unexpected";
			searchRepositoryByKeyword(keyword, responseFile);
			
			Assert.fail("expected HttpMessageNotReadableException");
			
		}catch(HttpMessageNotReadableException e){
		}
	}
	
	
	
	private List<RepositorySearch> searchRepositoryByKeyword(String keyword, String responseFile){
		
		String url = GitSearchConnectorImpl.GET_REPOSITORIES_BY_KEYWORD.replace("{keyword}", keyword);
		RestTemplate restTemplate = getRestTemplateMock(url, responseFile);
		GitSearchConnectorImpl gitsearchyConnector = new GitSearchConnectorImpl(restTemplate);
		return gitsearchyConnector.searchRepositoryByKeyword(keyword);
	}
	
	
}