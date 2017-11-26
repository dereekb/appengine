package com.dereekb.gae.server.datastore.models.impl;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link UniqueModel} implementation.
 *
 * @author dereekb
 *
 */
public class UniqueModelImpl extends AbstractUniqueModel {

	protected ModelKey modelKey;

	public UniqueModelImpl(ModelKey modelKey) {
		this.setModelKey(modelKey);
	}

	@Override
	public ModelKey getModelKey() {
		return this.modelKey;
	}

	public void setModelKey(ModelKey modelKey) {
		this.modelKey = modelKey;
	}

	@Override
	public String toString() {
		return "UniqueModelImpl [modelKey=" + this.modelKey + "]";
	}

}
