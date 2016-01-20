package com.sample.patterns.circuitbreaker;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class InMemoryCircuitBreakerStateStore implements
		ICircuitBreakerStateStore {

	private static final String STATE = "STATE";
	private static final String OPEN = "OPEN";
	private static final String CLOSED = "CLOSED";
	private static final String HALF_OPEN = "HALF_OPEN";

	private Map<String, String> statesMap = new LinkedHashMap<>();
	private CircuitBreakerStateEnum state;

	private Exception lastException;

	private Date lastStateChangedDateUtc;

	public InMemoryCircuitBreakerStateStore() {
		state = CircuitBreakerStateEnum.CLOSED;
		// statesMap.put(STATE, CLOSED);
	}

	@Override
	public void Trip(Exception ex) {
		state = CircuitBreakerStateEnum.OPEN;
		// statesMap.put(STATE, OPEN);
		lastException = ex;
	}

	@Override
	public void Reset() {
		state = CircuitBreakerStateEnum.CLOSED;
		// statesMap.put(STATE, CLOSED);
	}

	@Override
	public void HalfOpen() {
		state = CircuitBreakerStateEnum.HALFOPEN;
		// statesMap.put(STATE, HALF_OPEN);

	}

	public CircuitBreakerStateEnum getState() {
		return state;
	}

	public void setState(CircuitBreakerStateEnum state) {
		this.state = state;
	}

	public Exception getLastException() {
		return lastException;
	}

	public void setLastException(Exception lastException) {
		this.lastException = lastException;
	}

	public Date getLastStateChangedDateUtc() {
		return lastStateChangedDateUtc;
	}

	public void setLastStateChangedDateUtc(Date lastStateChangedDateUtc) {
		this.lastStateChangedDateUtc = lastStateChangedDateUtc;
	}

	@Override
	public String state() {
		// TODO Auto-generated method stub
		return state.name();
	}

	@Override
	public Exception lastException() {
		// TODO Auto-generated method stub
		return lastException;
	}

	@Override
	public Date lastChangedDateUtc() {
		// TODO Auto-generated method stub
		return lastStateChangedDateUtc;
	}

	
	@Override
	public boolean isClosed() {
		boolean closedStatus = false;
		if (CLOSED.equals(state.name())) {
			closedStatus = true;
		}
		return closedStatus;
	}
}
