package org.kwet.giteway.model;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Commit implements Serializable, Comparable<Commit> {

	private String message;

	private String login;

	private Date date;

	public String getMessage() {
		return message;
	}

	public String getLogin() {
		return login;
	}

	public Date getDate() {
		return date;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public int compareTo(Commit o) {
		return date.compareTo(o.getDate());
	}

	@Override
	public String toString() {
		return "Commit [message=" + message + ", login=" + login + ", date=" + date + "]";
	}

}