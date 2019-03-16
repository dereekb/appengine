package com.dereekb.gae.client.api.service.response.exception;

/**
 * Thrown when the response cannot be serialized.
 * 
 * @author dereekb
 *
 */
public class ClientResponseSerializationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ClientResponseSerializationException() {
		super();
	}

	public ClientResponseSerializationException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ClientResponseSerializationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClientResponseSerializationException(String message) {
		super(message);
	}

	public ClientResponseSerializationException(Throwable cause) {
		super(cause);
	}

	@Override
	public String toString() {
		return "ClientResponseSerializationException []";
	}

}
