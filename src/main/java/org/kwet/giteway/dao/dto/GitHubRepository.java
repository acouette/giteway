package org.kwet.giteway.dao.dto;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author a.couette
 *
 */
@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubRepository implements Serializable {

	private GitHubUser owner;

	private String name;

	private String description;

	public GitHubUser getOwner() {
		return owner;
	}

	public void setOwner(GitHubUser owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
