package org.kwet.giteway.dao.dto;

import org.kwet.giteway.model.Commit;
import org.kwet.giteway.model.Repository;
import org.kwet.giteway.model.User;

/**
 * @author a.couette
 *
 */
public final class DtoToModel {

	private DtoToModel(){
		
	}
	
	public static User getUser(GitHubUser gitHubUser) {
		if(gitHubUser==null){
			return null;
		}
		User user = new User();
		user.setLogin(gitHubUser.getLogin());
		user.setAvatarUrl(gitHubUser.getAvatarUrl());
		return user;
	}

	public static Repository getRepository(GitHubRepositorySearch gitHubRepositorySearch) {
		if(gitHubRepositorySearch==null){
			return null;
		}
		Repository repository = new Repository();
		repository.setName(gitHubRepositorySearch.getName());
		repository.setDescription(gitHubRepositorySearch.getDescription());
		repository.setOwner(gitHubRepositorySearch.getUsername());
		return repository;
	}

	public static Repository getRepository(GitHubRepository gitHubRepository) {
		if(gitHubRepository==null){
			return null;
		}
		Repository repository = new Repository();
		repository.setName(gitHubRepository.getName());
		repository.setOwner(gitHubRepository.getOwner().getLogin());
		repository.setDescription(gitHubRepository.getDescription());
		return repository;
	}

	public static Commit getCommit(GitHubCommit gitHubCommit) {
		if(gitHubCommit==null){
			return null;
		}
		Commit commit = new Commit();
		commit.setCommiter(getUser(gitHubCommit.getCommitter()));
		commit.setMessage(gitHubCommit.getCommit().getMessage());
		commit.setDate(gitHubCommit.getCommit().getCommitter().getDate());
		return commit;
	}

}
