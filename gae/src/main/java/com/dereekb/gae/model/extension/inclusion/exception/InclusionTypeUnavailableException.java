package com.dereekb.gae.model.extension.inclusion.exception;

import com.dereekb.gae.model.extension.read.exception.UnavailableTypesException;

/**
 * Exception for when a requested type is unavailable.
 *
 * @author dereekb
 */
public class InclusionTypeUnavailableException extends UnavailableTypesException {

	private static final long serialVersionUID = 1L;

	public InclusionTypeUnavailableException(String type) {
		super(type);
	}

}
