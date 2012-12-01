package org.kwet.giteway.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Repository implements Serializable {

	private String name;

	private String owner;

	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Repository [name=" + name + ", owner=" + owner + "]";
	}

}
