package com.dereekb.gae.utilities.collections.set.dencoder.exception;

import com.dereekb.gae.utilities.collections.set.dencoder.SetEncoder;

/**
 * Thrown by {@link SetEncoder} if the encoding fails.
 * 
 * @author dereekb
 *
 */
public class SetEncoderException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public SetEncoderException() {
		super();
	}

	public SetEncoderException(String message, Throwable cause) {
		super(message, cause);
	}

	public SetEncoderException(String s) {
		super(s);
	}

	public SetEncoderException(Throwable cause) {
		super(cause);
	}

}
