package org.kwet.giteway.data.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kwet.giteway.data.GitCommitterConnector;
import org.kwet.giteway.model.Committer;
import org.kwet.giteway.model.Repository;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class) 
public class GitCommiterConnectorTest extends BaseGitConnectorTest{
	
	private static Repository repository;
	
	@BeforeClass
	public static void beforeClass(){
		repository = new Repository();
		repository.setName("play!");
		repository.setUsername("Zenexity");
	}
	
	
	@Test
	public void testFindByRepository() throws Exception{

		String responseFile = "committers";
		List<Committer> committers = findByRepository(repository,responseFile);
		
		Assert.assertNotNull(committers);
		Assert.assertEquals(2,committers.size());
		Committer committer0 = committers.get(0);
		Assert.assertEquals(301810l,committer0.getId());
		Assert.assertEquals("cbeams",committer0.getLogin());
		Assert.assertEquals("https://secure.gravatar.com/avatar/29490cd51d5a93b61cc946844f471589?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png",committer0.getAvatarUrl());
	}
	
	private List<Committer> findByRepository(Repository repository, String responseFile){
		
		String url = GitCommitterConnectorImpl.GET_COMMITTERS_BY_REPOSITORY
				.replace("{username}", repository.getUsername())
				.replace("{name}", repository.getName());
		
		RestTemplate restTemplate = getRestTemplateMock(url, responseFile);
		GitCommitterConnector gitCommitterConnector = new GitCommitterConnectorImpl(restTemplate);
		return gitCommitterConnector.findByRepository(repository);
	}
	
	
}