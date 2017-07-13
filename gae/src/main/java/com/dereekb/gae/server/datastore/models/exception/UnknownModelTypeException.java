package com.dereekb.gae.server.datastore.models.exception;

/**
 * Exception thrown in cases where an unknown type is requested.
 * 
 * @author dereekb
 *
 */
public class UnknownModelTypeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String type;

	public UnknownModelTypeException(String type) {
		super("The unknown type '" + type + "' was encountered.");
		this.type = type;
	}

	protected String getType() {
		return this.type;
	}

	
	protected void setType(String type) {
		if (type == null) {
			throw new IllegalArgumentException("type cannot be null.");
		}
	
		this.type = type;
	}

	@Override
	public String toString() {
		return "UnknownModelTypeException [type=" + this.type + "]";
	}
	
}
