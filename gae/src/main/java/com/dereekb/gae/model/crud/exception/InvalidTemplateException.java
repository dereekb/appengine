package com.dereekb.gae.model.crud.exception;

import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

/**
 * Exception thrown to signify that a passed template cannot be used.
 *
 * @author dereekb
 * 
 * @deprecated Better to use {@link InvalidAttributeException}.
 */
@Deprecated
public class InvalidTemplateException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidTemplateException(String message) {
		super(message);
	}

	public InvalidTemplateException(Throwable cause) {
		super(cause);
	}

	@Override
	public String toString() {
		return "InvalidTemplateException []";
	}

}
