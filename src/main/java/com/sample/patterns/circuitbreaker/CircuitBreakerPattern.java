package com.sample.patterns.circuitbreaker;

public class CircuitBreakerPattern {

	public static void main(String[] args) {
		CircuitBreaker circuitBreaker = new CircuitBreaker();
		try {
			String res = circuitBreaker.executeAction();
			System.out.println("Response: " + res);
			Thread.sleep(60000);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		try {
			String res2 = circuitBreaker.executeAction();
			System.out.println("Response: " + res2);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		try {
			String res2 = circuitBreaker.executeAction();
			System.out.println("Response: " + res2);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
