package com.dereekb.gae.web.api.model.crud.controller.impl;

import java.util.Collection;

import com.dereekb.gae.server.datastore.models.impl.TypedModelImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.web.api.model.crud.controller.ReadControllerExistsRequest;

/**
 * {@link ReadControllerExistsRequest} implementation.
 *
 * @author dereekb
 *
 */
public class ReadControllerExistsRequestImpl extends TypedModelImpl
        implements ReadControllerExistsRequest {

	private boolean atomic;
	private Collection<ModelKey> modelKeys;

	public ReadControllerExistsRequestImpl(String modelType, boolean atomic, Collection<ModelKey> modelKeys) {
		super(modelType);
		this.setAtomic(atomic);
		this.setModelKeys(modelKeys);
	}

	// MARK: ReadControllerExistsRequest
	@Override
	public boolean isAtomic() {
		return this.atomic;
	}

	public void setAtomic(boolean atomic) {
		this.atomic = atomic;
	}

	@Override
	public Collection<ModelKey> getModelKeys() {
		return this.modelKeys;
	}

	public void setModelKeys(Collection<ModelKey> modelKeys) {
		if (modelKeys == null) {
			throw new IllegalArgumentException("modelKeys cannot be null.");
		}

		this.modelKeys = modelKeys;
	}

	@Override
	public String toString() {
		return "ReadControllerExistsRequestImpl [atomic=" + this.atomic + ", modelKeys=" + this.modelKeys + "]";
	}

}
