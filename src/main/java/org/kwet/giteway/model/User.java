package org.kwet.giteway.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class User implements Serializable {

	private String login;

	private String avatarUrl;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	@Override
	public String toString() {
		return "User [login=" + login + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		User other = (User) obj;
		if (login == null) {
			if (other.login != null){
				return false;
			}
		} else if (!login.equals(other.login)){
			return false;
		}
		return true;
	}

}
