package com.dereekb.gae.model.crud.exception;

import com.dereekb.gae.model.crud.task.impl.AtomicTaskImpl;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Used by an atomic task to capture expected failures/exceptions between
 * models.
 *
 * @author dereekb
 *
 * @see AtomicTaskImpl
 */
public class AtomicFunctionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private UniqueModel model;
	private RuntimeException cause;

	public AtomicFunctionException(UniqueModel model, RuntimeException cause) throws IllegalArgumentException {
		this.setModel(model);
		this.setCause(cause);
	}

	public UniqueModel getModel() {
		return this.model;
	}

	public void setModel(UniqueModel model) throws IllegalArgumentException {
		if (model == null) {
			throw new IllegalArgumentException("model cannot be null.");
		}

		this.model = model;
	}

	@Override
	public RuntimeException getCause() {
		return this.cause;
	}

	public void setCause(RuntimeException cause) throws IllegalArgumentException {
		if (cause == null) {
			throw new IllegalArgumentException("cause cannot be null.");
		}

		this.cause = cause;
	}

}
