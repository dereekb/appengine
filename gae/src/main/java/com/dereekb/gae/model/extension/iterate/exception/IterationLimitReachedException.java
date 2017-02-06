package com.dereekb.gae.model.extension.iterate.exception;

import com.dereekb.gae.model.extension.iterate.IterateTaskExecutor;

/**
 * Thrown when a {@link IterateTaskExecutor} has reached it's iteration limit.
 * <p>
 * A cursor is provided to allow the system to continue.
 *
 * @author dereekb
 *
 */
public class IterationLimitReachedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String cursor;

	public IterationLimitReachedException(String cursor) {
		this.setCursor(cursor);
	}

	public String getCursor() {
		return this.cursor;
	}

	public void setCursor(String cursor) {
		this.cursor = cursor;
	}

	@Override
	public String toString() {
		return "IterationLimitReachedException [cursor=" + this.cursor + "]";
	}

}
