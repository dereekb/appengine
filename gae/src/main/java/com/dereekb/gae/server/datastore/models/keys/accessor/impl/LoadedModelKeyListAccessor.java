package com.dereekb.gae.server.datastore.models.keys.accessor.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;

/**
 * {@link
 * @author dereekb
 *
 * @param <T>
 */
public class LoadedModelKeyListAccessor<T extends UniqueModel>
        implements ModelKeyListAccessor<T> {

	private List<T> models;
	private List<ModelKey> keys;

	public LoadedModelKeyListAccessor(Collection<T> models) {
		this.models = new ArrayList<T>(models);
	}

	@Override
	public List<T> getModels() {
		return this.models;
	}

	public void setModels(List<T> models) {
		this.models = models;
	}

	@Override
	public List<ModelKey> getModelKeys() {
		if (this.keys == null) {
			this.keys = ModelKey.readModelKeys(this.models);
		}

		return this.keys;
	}

	@Override
	public String toString() {
		return "LoadedModelKeyListAccessor [models=" + this.models + "]";
	}

}
