package com.dereekb.gae.server.storage.exception;

/**
 * Exception for when a storable object, such as a file or folder, cannot be
 * generated.
 *
 * @author dereekb
 *
 */
public class StorableObjectGenerationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public StorableObjectGenerationException() {
		super();
	}

	public StorableObjectGenerationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public StorableObjectGenerationException(String message, Throwable cause) {
		super(message, cause);
	}

	public StorableObjectGenerationException(String message) {
		super(message);
	}

	public StorableObjectGenerationException(Throwable cause) {
		super(cause);
	}

	@Override
	public String toString() {
		return "StorableObjectGenerationException []";
	}

}
