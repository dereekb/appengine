package com.dereekb.gae.server.event.event.exception;

/**
 * {@link Event} related exception.
 *
 * @author dereekb
 *
 */
public class EventException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EventException() {
		super();
	}

	public EventException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EventException(String message, Throwable cause) {
		super(message, cause);
	}

	public EventException(String message) {
		super(message);
	}

	public EventException(Throwable cause) {
		super(cause);
	}

}
