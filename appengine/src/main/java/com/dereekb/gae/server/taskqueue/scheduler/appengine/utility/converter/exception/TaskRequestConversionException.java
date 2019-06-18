package com.dereekb.gae.server.taskqueue.scheduler.appengine.utility.converter.exception;

/**
 * {@link TaskRequestConversionException}
 *
 * @author dereekb
 *
 */
public class TaskRequestConversionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TaskRequestConversionException() {
		super();
	}

	public TaskRequestConversionException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TaskRequestConversionException(String message, Throwable cause) {
		super(message, cause);
	}

	public TaskRequestConversionException(String message) {
		super(message);
	}

	public TaskRequestConversionException(Throwable cause) {
		super(cause);
	}

}
