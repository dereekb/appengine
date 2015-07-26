package com.dereekb.gae.model.extension.inclusion.exception;

import com.dereekb.gae.model.extension.read.exception.UnavailableTypeException;

/**
 * Exception for when a requested type is unavailable.
 *
 * @author dereekb
 */
public class InclusionTypeUnavailableException extends UnavailableTypeException {

	private static final long serialVersionUID = 1L;

	public InclusionTypeUnavailableException(String type) {
		super(type);
	}

}
