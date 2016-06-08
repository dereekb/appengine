package com.dereekb.gae.model.exception;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Exception for {@link ModelKey} based requests that require a model that is
 * unavailable.
 *
 * @author dereekb
 *
 */
public class UnavailableModelException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final ModelKey modelKey;

	public UnavailableModelException(String message) {
		this(message, null);
	}

	public UnavailableModelException(ModelKey modelKey) {
		this.modelKey = modelKey;
	}

	public UnavailableModelException(String message, ModelKey modelKey) {
		super(message);
		this.modelKey = modelKey;
	}

	public ModelKey getModelKey() {
		return this.modelKey;
	}

	@Override
	public String toString() {
		return "UnavailableModelException [modelKey=" + this.modelKey + "]";
	}

}
