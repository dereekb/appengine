package com.dereekb.gae.server.datastore.models.keys.accessor.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;

/**
 * {@link ModelKeyListAccessor} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class LoadedModelKeyListAccessor<T extends UniqueModel>
        implements ModelKeyListAccessor<T> {

	private String modelType;
	
	private List<T> models;
	private List<ModelKey> keys; // lazy loaded

	public LoadedModelKeyListAccessor(String modelType, Collection<T> models) throws IllegalArgumentException {
		this.setModelType(modelType);
		this.setModels(models);
	}

	@Override
	public String getModelType() {
		return this.modelType;
	}

	public void setModelType(String modelType) throws IllegalArgumentException {
		if (modelType == null) {
			throw new IllegalArgumentException("Model type cannot be null.");
		}

		this.modelType = modelType;
	}

	@Override
	public List<T> getModels() {
		return this.models;
	}

	public void setModels(Collection<T> models) {
		if (models != null) {
			this.models = new ArrayList<T>(models);
		} else {
			this.models = Collections.emptyList();
		}
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
