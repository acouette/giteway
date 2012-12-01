package org.kwet.giteway.dao.dto;

import org.kwet.giteway.model.Commit;
import org.kwet.giteway.model.Repository;
import org.kwet.giteway.model.User;

/**
 * @author Antoine Couette
 *
 */
public class DtoToModel {

	public static User getUser(GitHubUser gitHubUser) {
		User user = new User();
		user.setLogin(gitHubUser.getLogin());
		user.setAvatarUrl(gitHubUser.getAvatarUrl());
		return user;
	}

	public static Repository getRepository(GitHubRepositorySearch gitHubRepositorySearch) {
		Repository repository = new Repository();
		repository.setName(gitHubRepositorySearch.getName());
		repository.setDescription(gitHubRepositorySearch.getDescription());
		repository.setOwner(gitHubRepositorySearch.getUsername());
		return repository;
	}

	public static Repository getRepository(GitHubRepository gitHubRepository) {
		Repository repository = new Repository();
		repository.setName(gitHubRepository.getName());
		repository.setOwner(gitHubRepository.getOwner().getLogin());
		repository.setDescription(gitHubRepository.getDescription());
		return repository;
	}

	public static Commit getCommit(GitHubCommit gitHubCommit) {
		Commit commit = new Commit();
		commit.setCommiter(getUser(gitHubCommit.getCommitter()));
		commit.setMessage(gitHubCommit.getCommit().getMessage());
		commit.setDate(gitHubCommit.getCommit().getCommitter().getDate());
		return commit;
	}

}
