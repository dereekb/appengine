package com.dereekb.gae.server.datastore.models.exception;

import com.dereekb.gae.server.datastore.models.TypedModel;

/**
 * Exception thrown in cases where an illegal type is requested.
 *
 * @author dereekb
 *
 */
public class ModelTypeException extends RuntimeException
        implements TypedModel {

	private static final long serialVersionUID = 1L;

	private String type;

	public ModelTypeException(String type) {
		this(type, "The illegal type '" + type + "' was encountered.");
	}

	public ModelTypeException(String type, String message) {
		super(message);
		this.setType(type);
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

	// MARK: TypedModel
	@Override
	public String getModelType() {
		return this.type;
	}

	@Override
	public String toString() {
		return "UnknownModelTypeException [type=" + this.type + "]";
	}

}
