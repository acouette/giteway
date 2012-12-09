package org.kwet.giteway.dao.dto;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubUsers implements Serializable{

	private List<GitHubUser> users;

	public List<GitHubUser> getUsers() {
		return users;
	}

	public void setUsers(List<GitHubUser> users) {
		this.users = users;
	}
}
