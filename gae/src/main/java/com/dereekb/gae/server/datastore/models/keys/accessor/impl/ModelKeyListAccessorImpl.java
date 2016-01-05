package com.dereekb.gae.server.datastore.models.keys.accessor.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;

/**
 * {@link ModelKeyListAccessor} implementation
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ModelKeyListAccessorImpl<T extends UniqueModel>
        implements ModelKeyListAccessor<T> {

	private final String modelType;
	private final Getter<T> getter;

	private List<ModelKey> keys;
	private List<T> models; // lazy

	public ModelKeyListAccessorImpl(String modelType, Getter<T> getter, Collection<ModelKey> keys)
	        throws IllegalArgumentException {
		this(modelType, getter);
		this.setKeys(keys);
	}

	public ModelKeyListAccessorImpl(String modelType, Getter<T> getter) throws IllegalArgumentException {
		if (getter == null) {
			throw new IllegalArgumentException("Getter cannot be null.");
		}

		if (modelType == null) {
			throw new IllegalArgumentException("Model type cannot be null.");
		}

		this.getter = getter;
		this.modelType = modelType;
	}

	@Override
	public String getModelType() {
		return this.modelType;
	}

	@Override
	public List<ModelKey> getModelKeys() {
		return this.keys;
	}

	public void setKeys(Collection<ModelKey> keys) {
		if (keys == null) {
			this.keys = Collections.emptyList();
		} else {
			this.keys = new ArrayList<ModelKey>(keys);
		}
	}

	@Override
	public List<T> getModels() {
		if (this.models == null) {
			this.models = this.loadModels();
		}

		return this.models;
	}

	private List<T> loadModels() {
		return this.getter.getWithKeys(this.keys);
	}

	@Override
	public String toString() {
		return "ModelKeyListAccessorImpl [keys=" + this.keys + ", getter=" + this.getter + "]";
	}

}
