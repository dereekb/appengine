package com.dereekb.gae.server.datastore.models.exception;

/**
 * Exception thrown in cases where an illegal type is requested.
 *
 * @author dereekb
 *
 */
public class IllegalModelTypeException extends ModelTypeException {

	private static final long serialVersionUID = 1L;

	public IllegalModelTypeException(String type) {
		super(type, "The unknown type '" + type + "' was encountered.");
	}

	@Override
	public String toString() {
		return "IllegalModelTypeException [type=" + this.getType() + "]";
	}

}
