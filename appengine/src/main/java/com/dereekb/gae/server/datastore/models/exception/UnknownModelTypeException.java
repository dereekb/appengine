package com.dereekb.gae.server.datastore.models.exception;

/**
 * Exception thrown in cases where an unknown type is requested.
 *
 * @author dereekb
 *
 */
public class UnknownModelTypeException extends ModelTypeException {

	private static final long serialVersionUID = 1L;

	public UnknownModelTypeException(String type) {
		super(type, "The unknown type '" + type + "' was encountered.");
	}

	@Override
	public String toString() {
		return "UnknownModelTypeException [type=" + this.getType() + "]";
	}

}
