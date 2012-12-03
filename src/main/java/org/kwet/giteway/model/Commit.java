package org.kwet.giteway.model;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Commit implements Serializable, Comparable<Commit> {
	
	private String message;

	private User commiter;

	private Date date;

	public String getMessage() {
		return message;
	}

	public Date getDate() {
		return date;
	}

	public User getCommiter() {
		return commiter;
	}

	public void setCommiter(User commiter) {
		this.commiter = commiter;
	}

	public void setMessage(String message) {
		this.message = message;
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
		return "Commit [message=" + message + ", commiter=" + commiter + ", date=" + date + "]";
	}
	

}