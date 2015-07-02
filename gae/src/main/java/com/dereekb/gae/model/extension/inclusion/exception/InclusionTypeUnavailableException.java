package com.dereekb.gae.model.extension.inclusion.exception;

/**
 * Exception for when a requested type is unavailable.
 *
 * @author dereekb
 */
public class InclusionTypeUnavailableException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String type;

	public InclusionTypeUnavailableException(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

}
