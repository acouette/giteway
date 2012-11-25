package org.kwet.giteway.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CommitterActivity implements Serializable{

	private String login;
	
	private int percentage;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public int getPercentage() {
		return percentage;
	}

	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}

	public CommitterActivity(String login, int percentage) {
		super();
		this.login = login;
		this.percentage = percentage;
	}

	@Override
	public String toString() {
		return "CommitterActivity [login=" + login + ", percentage=" + percentage + "]";
	}
	
	
	
}
