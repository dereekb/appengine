package com.dereekb.gae.server.datastore.exception;

import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Thrown when an exception occurs while attempting to store a model that
 * already has an identifier.
 *
 * @author dereekb
 *
 */
public class StoreKeyedEntityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private UniqueModel model;

	public StoreKeyedEntityException(UniqueModel model) {
		this.setModel(model);
	}

	public UniqueModel getModel() {
		return this.model;
	}

	public void setModel(UniqueModel model) {
		if (model == null) {
			throw new IllegalArgumentException("model cannot be null.");
		}

		this.model = model;
	}

}
