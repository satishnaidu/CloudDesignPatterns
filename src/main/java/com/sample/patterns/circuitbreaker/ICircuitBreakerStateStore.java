package com.sample.patterns.circuitbreaker;

import java.util.Date;

public interface ICircuitBreakerStateStore {

	String state();

	Exception lastException();

	Date lastChangedDateUtc();

	void setLastStateChangedDateUtc(Date lastStateChangedDateUtc);

	void Trip(Exception ex);

	void Reset();

	void HalfOpen();

	boolean isClosed();
}
