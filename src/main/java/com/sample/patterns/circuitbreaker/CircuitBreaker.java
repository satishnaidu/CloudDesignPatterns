package com.sample.patterns.circuitbreaker;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class CircuitBreaker {

	private ICircuitBreakerStateStore circuitBreakerStateStore;

	private Object halfOpenSyncObject = new Object();

	private long openToHalfOpenWaitTime = 60000l;

	private static int failCount = 0;

	private static int successCount = 0;

	public CircuitBreaker() {
		circuitBreakerStateStore = new InMemoryCircuitBreakerStateStore();

	}

	public boolean isClosed() {
		return circuitBreakerStateStore.isClosed();
	}

	public boolean isOpen() {
		return !circuitBreakerStateStore.isClosed();
	}

	public String executeAction() throws Exception {
		String response = "";
		if (isOpen()) {
			System.out.println("Entered in is open state");
			// The circuit breaker is Open. Check if the Open timeout has
			// expired.
			// If it has, set the state to HalfOpen. Another approach may be to
			// simply
			// check for the HalfOpen state that had be set by some other
			// operation.
			Date lastChangedUtc = circuitBreakerStateStore.lastChangedDateUtc();
			Date lastThreasholdDate = null;
			if (lastChangedUtc == null) {
				Calendar cal = Calendar.getInstance();
				long lastChangedUtcMillis = cal.getTimeInMillis();
				cal.setTimeInMillis(lastChangedUtcMillis
						+ openToHalfOpenWaitTime);
				lastThreasholdDate = cal.getTime();
				circuitBreakerStateStore
						.setLastStateChangedDateUtc(lastThreasholdDate);
			}

			if (lastThreasholdDate != null
					&& lastThreasholdDate.compareTo(new Date()) < 0) {

				System.out
						.println("Open timeout has expired, allow one operation to execute");

				// The Open timeout has expired. Allow one operation to execute.
				// Note that, in
				// this example, the circuit breaker is simply set to HalfOpen
				// after being
				// in the Open state for some period of time. An alternative
				// would be to set
				// this using some other approach such as a timer, test method,
				// manually, and
				// so on, and simply check the state here to determine how to
				// handle execution
				// of the action.
				// Limit the number of threads to be executed when the breaker
				// is HalfOpen.
				// An alternative would be to use a more complex approach to
				// determine which
				// threads or how many are allowed to execute, or to execute a
				// simple test
				// method instead.
				// boolean lockTaken = false;
				// synchronized (halfOpenSyncObject) {
				try {

					// if (lockTaken) {
					// Set the circuit breaker state to HalfOpen.
					System.out.println("Before changing status to half open");
					circuitBreakerStateStore.HalfOpen();
					// Attempt the operation.
					response = action();
					// If this action succeeds, reset the state and
					// allow
					// other operations.
					// In reality, instead of immediately returning to
					// the
					// Open state, a counter
					// here would record the number of successful
					// operations
					// and return the
					// circuit breaker to the Open state only after a
					// specified number succeed.
					successCount++;
					if (successCount == 1) {
						System.out
								.println("Success count is reached, resetting state to closed");
						this.circuitBreakerStateStore.Reset();
					}
					return response;
					// }
				} catch (Exception ex) {
					// If there is still an exception, trip the breaker
					// again
					// immediately.
					// Throw the exception so that the caller knows which
					// exception occurred.
					System.out
							.println("Again exception came in half open state, so state is still open");
					throw ex;
				}
			}
			// The Open timeout has not yet expired. Throw a
			// CircuitBreakerOpen exception to
			// inform the caller that the caller that the call was not
			// actually attempted,
			// and return the most recent exception received.
			System.out
					.println("Open timout not expired, so returning last exception without calling service");
			throw new CircuitBreakerOpenException(
					circuitBreakerStateStore.lastException());
		}

		// }

		// The circuit breaker is Closed, execute the action.
		try {
			response = action();

		} catch (Exception ex) {
			// If an exception still occurs here, simply
			// re-trip the breaker immediately.
			this.TrackException(ex);
			// Throw the exception so that the caller can tell
			// the type of exception that was thrown.
			throw ex;
		}
		return response;
	}

	private void TrackException(Exception ex) {
		// For simplicity in this example, open the circuit breaker on the first
		// exception.
		// In reality this would be more complex. A certain type of exception,
		// such as one
		// that indicates a service is offline, might trip the circuit breaker
		// immediately.
		// Alternatively it may count exceptions locally or across multiple
		// instances and
		// use this value over time, or the exception/success ratio based on the
		// exception
		// types, to open the circuit breaker.

		failCount++;

		if (failCount == 1) {
			System.out
					.println("Failure count reached tripping exception to Open state");
			this.circuitBreakerStateStore.Trip(ex);
		}
	}

	public static String action() throws IOException {
		System.out.println("main action invoked ");
		String url = "http://10.78.203.703/neDataAccess/export/ActiveElements.xml";
		String res = HttpClientUtil.getHttpData(url);
		return res;

	}
}
