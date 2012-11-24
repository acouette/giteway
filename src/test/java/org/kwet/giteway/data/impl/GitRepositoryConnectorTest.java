package org.kwet.giteway.data.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kwet.giteway.data.GitRepositoryConnector;
import org.kwet.giteway.model.Commit;
import org.kwet.giteway.model.Repository;
import org.kwet.giteway.model.User;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class) 
public class GitRepositoryConnectorTest extends BaseGitConnectorTest{
	
	private static Repository repository;
	
	@BeforeClass
	public static void beforeClass(){
		repository = new Repository();
		repository.setName("play!");
		User user = new User();
		user.setLogin("Zenexity");
		repository.setOwner(user);
	}
	
	@Test
	public void testFind(){
		String name = "Hello-World";
		String owner = "octocat";
		String responseFile = "repo"; 
		
		String url = GitRepositoryConnectorImpl.GET_REPOSITORY.replace("{owner}", owner).replace("{name}", name);
		
		RestTemplate restTemplate = getRestTemplateMock(url, responseFile);
		GitRepositoryConnector gitRepositoryConnector = new GitRepositoryConnectorImpl(restTemplate);
		Repository repository = gitRepositoryConnector.find(owner,name);
		Assert.assertNotNull(repository);
		
		Assert.assertEquals("octocat", repository.getOwner().getLogin());
		
	}
	
	
	@Test
	public void testFindCollaborators() throws Exception{

		String responseFile = "repo-collaborators";
		List<User> collaborators = findCollaborators(responseFile);
		
		Assert.assertNotNull(collaborators);
		Assert.assertEquals(2,collaborators.size());
		User collaborator = collaborators.get(0);
		Assert.assertEquals(301810l,collaborator.getId());
		Assert.assertEquals("cbeams",collaborator.getLogin());
		Assert.assertEquals("https://secure.gravatar.com/avatar/29490cd51d5a93b61cc946844f471589?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png",collaborator.getAvatarUrl());
	}
	
	private List<User> findCollaborators(String responseFile){
		
		String url = GitRepositoryConnectorImpl.GET_COLLABORATORS
				.replace("{owner}", repository.getOwner().getLogin())
				.replace("{name}", repository.getName());
		
		RestTemplate restTemplate = getRestTemplateMock(url, responseFile);
		GitRepositoryConnectorImpl gitRepositoryConnectorImpl = new GitRepositoryConnectorImpl(restTemplate);
		return gitRepositoryConnectorImpl.findCollaborators(repository);
	}
	
	@Test
	public void testFindCommits() throws Exception{

		String responseFile = "repo-commits";
		List<Commit> commits = findCommits(responseFile);
		
		Assert.assertNotNull(commits);
		Assert.assertEquals(2,commits.size());
		Commit commit = commits.get(0);
		Assert.assertEquals("Set the ApplicationContext prop of ExceptionResolver...",commit.getMessage());
		Assert.assertNotNull(commit.getCommitter());
		
		Assert.assertEquals("rstoyanchev",commit.getCommitter().getLogin());
		Assert.assertEquals(401908,commit.getCommitter().getId());
	}
	
	private List<Commit> findCommits(String responseFile){
		
		String url = GitRepositoryConnectorImpl.GET_COMMITS
				.replace("{owner}", repository.getOwner().getLogin())
				.replace("{name}", repository.getName());
		
		RestTemplate restTemplate = getRestTemplateMock(url, responseFile);
		GitRepositoryConnectorImpl gitRepositoryConnectorImpl = new GitRepositoryConnectorImpl(restTemplate);
		return gitRepositoryConnectorImpl.findCommits(repository);
	}
	
	
	
	
}