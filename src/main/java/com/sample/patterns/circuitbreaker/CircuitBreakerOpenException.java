package com.sample.patterns.circuitbreaker;

public class CircuitBreakerOpenException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CircuitBreakerOpenException() {
		super();
	}

	public CircuitBreakerOpenException(Throwable cause) {
		super(cause);
	}

}
