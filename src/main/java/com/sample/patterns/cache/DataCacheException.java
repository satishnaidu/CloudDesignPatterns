package com.sample.patterns.cache;

public class DataCacheException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	private Throwable cause;

	public DataCacheException() {
		super();
	}

	public DataCacheException(String msg) {
		super(msg);
		this.message = msg;
	}

	public DataCacheException(String msg, Throwable cause) {
		super(msg, cause);
		this.message = msg;
		this.cause = cause;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Throwable getCause() {
		return cause;
	}

	public void setCause(Throwable cause) {
		this.cause = cause;
	}

}
