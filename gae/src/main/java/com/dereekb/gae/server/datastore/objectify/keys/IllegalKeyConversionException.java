package com.dereekb.gae.server.datastore.objectify.keys;

/**
 * Occurs when one or more keys cannot be converted.
 *
 * @author dereekb
 * @see ObjectifyKeyConverter
 */
public final class IllegalKeyConversionException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public IllegalKeyConversionException() {
		super("One or more keys were illegal.");
	}

	public IllegalKeyConversionException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalKeyConversionException(String s) {
		super(s);
	}

	public IllegalKeyConversionException(Throwable cause) {
		super(cause);
	}

}
