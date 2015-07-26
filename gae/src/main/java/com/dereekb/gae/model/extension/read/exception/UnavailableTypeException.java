package com.dereekb.gae.model.extension.read.exception;


/**
 * Exception for when a requested type is unavailable.
 *
 * @author dereekb
 */
public class UnavailableTypeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	protected final String type;

	public UnavailableTypeException(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	@Override
	public String toString() {
		return "UnavailableTypeException [type=" + this.type + "]";
	}

}
