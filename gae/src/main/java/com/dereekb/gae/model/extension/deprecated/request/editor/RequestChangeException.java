package com.dereekb.gae.model.extension.request.editor;

/**
 * Thrown when a {@link Request} fails to be created or deleted.
 *
 * @author dereekb
 */
public final class RequestChangeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RequestChangeException() {
		super();
	}

	public RequestChangeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RequestChangeException(String message, Throwable cause) {
		super(message, cause);
	}

	public RequestChangeException(String message) {
		super(message);
	}

	public RequestChangeException(Throwable cause) {
		super(cause);
	}

}
