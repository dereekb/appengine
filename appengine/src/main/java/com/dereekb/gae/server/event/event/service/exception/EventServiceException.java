package com.dereekb.gae.server.event.event.service.exception;

import com.dereekb.gae.server.event.event.service.EventService;

/**
 * {@link EventService} related exception.
 *
 * @author dereekb
 *
 */
public class EventServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EventServiceException() {
		super();
	}

	public EventServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EventServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public EventServiceException(String message) {
		super(message);
	}

	public EventServiceException(Throwable cause) {
		super(cause);
	}

}
