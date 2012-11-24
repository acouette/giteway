package org.kwet.giteway.data.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kwet.giteway.data.GitRepositoryConnector;
import org.kwet.giteway.data.impl.GitRepositoryConnectorImpl;
import org.kwet.giteway.model.Repository;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class) 
public class GitRepositoryConnectorTest extends BaseGitConnectorTest{
	
	@Test
	public void testFindByKeyword() throws Exception{

		String keyword = "matchingKeyword";
		String responseFile = "repos";
		List<Repository> repositories = findRepositoriesByKeyword(keyword, responseFile);
		
		Assert.assertNotNull(repositories);
		Assert.assertEquals(1,repositories.size());
		Repository repo0 = repositories.get(0);
		Assert.assertEquals("playframework-elasticsearch",repo0.getName());
		Assert.assertEquals("feliperazeek",repo0.getUsername());
		Assert.assertEquals("Integrate Elastic Search in a Play! Framework Application.",repo0.getDescription());
	}
	
	@Test
	public void testFindByKeywordEmpty() throws Exception{
		
		String keyword = "notMatchingKeyword";
		String responseFile = "repos-empty";
		List<Repository> repositories = findRepositoriesByKeyword(keyword, responseFile);
		
		Assert.assertNotNull(repositories);
		Assert.assertEquals(0,repositories.size());
	}
	
	@Test
	public void testFindByKeywordUnexpected() throws Exception{
		try{
			
			String keyword = "anyKeyword";
			String responseFile = "repos-unexpected";
			findRepositoriesByKeyword(keyword, responseFile);
			
			Assert.fail("expected HttpMessageNotReadableException");
			
		}catch(HttpMessageNotReadableException e){
		}
	}
	
	private List<Repository> findRepositoriesByKeyword(String keyword, String responseFile){
		
		String url = GitRepositoryConnectorImpl.GET_REPOSITORIES_BY_KEYWORD.replace("{keyword}", keyword);
		RestTemplate restTemplate = getRestTemplateMock(url, responseFile);
		GitRepositoryConnector gitRepositoryConnector = new GitRepositoryConnectorImpl(restTemplate);
		return gitRepositoryConnector.findByKeyword(keyword);
	}
	
	
}