package org.kwet.giteway.model;

/**
 * @author a.couette
 *
 */
@SuppressWarnings("serial")
public class GitewayRequestException extends RuntimeException {

	private int statusCode;
	
	private String message;
	
	private String uri;
	
	
	
	public GitewayRequestException() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public GitewayRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public GitewayRequestException(int statusCode, String message, String uri) {
		super("HTTP error code : " + statusCode + ". Reason : " + message+ ". Uri : " + uri);
		this.statusCode = statusCode;
		this.message = message;
		this.uri = uri;
	}

	public GitewayRequestException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public GitewayRequestException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public GitewayRequestException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getMessage() {
		return message;
	}

	public String getUri() {
		return uri;
	}
	
	

}
