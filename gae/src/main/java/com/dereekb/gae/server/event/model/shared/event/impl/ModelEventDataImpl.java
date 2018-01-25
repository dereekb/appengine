package com.dereekb.gae.server.event.model.shared.event.impl;

import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.server.event.model.shared.event.ModelEventData;

/**
 * {@link ModelEventData} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ModelEventDataImpl<T extends UniqueModel> extends AbstractModelEventDataImpl<T>
        implements ModelEventData<T> {

	private ModelKeyListAccessor<T> accessor;

	public ModelEventDataImpl(ModelKeyListAccessor<T> accessor) {
		super(accessor.getModelType());
		this.setAccessor(accessor);
	}

	public ModelKeyListAccessor<T> getAccessor() {
		return this.accessor;
	}

	public void setAccessor(ModelKeyListAccessor<T> accessor) {
		if (accessor == null) {
			throw new IllegalArgumentException("accessor cannot be null.");
		}

		this.accessor = accessor;
	}

	// MARK: Model
	@Override
	public List<T> getEventModels() {
		return this.getAccessor().getModels();
	}

	@Override
	public List<ModelKey> getEventModelKeys() {
		return this.getAccessor().getModelKeys();
	}

	@Override
	public String toString() {
		return "ModelEventDataImpl [accessor=" + this.accessor + "]";
	}

}