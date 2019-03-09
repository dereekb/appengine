package com.dereekb.gae.model.crud.exception;

import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Used by an atomic function to capture expected failures/exceptions.
 *
 * @author dereekb
 *
 */
public class AtomicFunctionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private UniqueModel model;
	private RuntimeException cause;

	public AtomicFunctionException() {}

	public AtomicFunctionException(UniqueModel model, RuntimeException cause) {
		this.model = model;
		this.cause = cause;
	}

	public UniqueModel getModel() {
		return this.model;
	}

	public void setModel(UniqueModel model) {
		this.model = model;
	}

	@Override
	public RuntimeException getCause() {
		return this.cause;
	}

	public void setCause(RuntimeException cause) {
		this.cause = cause;
	}

}
