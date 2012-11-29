package org.kwet.giteway.dao.dto;

import org.kwet.giteway.model.Commit;
import org.kwet.giteway.model.Repository;
import org.kwet.giteway.model.User;

public class DtoToModel {

	public static User getUser(GitHubUser gitHubUser) {
		User user = new User();
		user.setId(gitHubUser.getId());
		user.setLogin(gitHubUser.getLogin());
		user.setAvatarUrl(gitHubUser.getAvatarUrl());
		return user;
	}

	public static Repository getRepository(GitHubRepositorySearch gitHubRepositorySearch) {
		Repository repository = new Repository();
		repository.setName(gitHubRepositorySearch.getName());
		repository.setUsername(gitHubRepositorySearch.getUsername());
		repository.setDescription(gitHubRepositorySearch.getDescription());
		return repository;
	}

	public static Repository getRepository(GitHubRepository gitHubRepository) {
		Repository repository = new Repository();
		repository.setName(gitHubRepository.getName());
		repository.setUsername(gitHubRepository.getOwner().getLogin());
		repository.setDescription(gitHubRepository.getDescription());
		return repository;
	}

	public static Commit getCommit(GitHubCommit gitHubCommit) {
		Commit commit = new Commit();
		commit.setLogin(gitHubCommit.getCommitter().getLogin());
		commit.setMessage(gitHubCommit.getCommit().getMessage());
		commit.setDate(gitHubCommit.getCommit().getCommitter().getDate());
		return commit;
	}

}
