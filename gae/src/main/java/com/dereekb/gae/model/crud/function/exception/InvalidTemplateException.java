package com.dereekb.gae.model.crud.function.exception;

import com.dereekb.gae.model.crud.function.delegate.CreateFunctionDelegate;

/**
 * Exception used by {@link CreateFunctionDelegate} to signify that the passed
 * template cannot be used.
 *
 * @author dereekb
 *
 */
public class InvalidTemplateException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidTemplateException(String message) {
		super(message);
	}

	public InvalidTemplateException(Throwable cause) {
		super(cause);
	}

}
