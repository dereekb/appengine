package com.dereekb.gae.model.general.people.website.exception;

import com.dereekb.gae.model.general.people.website.WebsiteAddress;

/**
 * Thrown when the data in a {@link WebsiteAddress} is determined to be invalid.
 *
 * @author dereekb
 *
 */
public class InvalidWebsiteDataException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidWebsiteDataException() {
		super();
	}

	public InvalidWebsiteDataException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidWebsiteDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidWebsiteDataException(String message) {
		super(message);
	}

	public InvalidWebsiteDataException(Throwable cause) {
		super(cause);
	}

}
