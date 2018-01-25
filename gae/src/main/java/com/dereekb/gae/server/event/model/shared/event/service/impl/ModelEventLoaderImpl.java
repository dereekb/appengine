package com.dereekb.gae.server.event.model.shared.event.service.impl;

import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.exception.IllegalModelTypeException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessorFactory;
import com.dereekb.gae.server.event.event.EventType;
import com.dereekb.gae.server.event.model.shared.event.ModelEvent;
import com.dereekb.gae.server.event.model.shared.event.ModelEventData;
import com.dereekb.gae.server.event.model.shared.event.ModelKeyEvent;
import com.dereekb.gae.server.event.model.shared.event.ModelKeyEventData;
import com.dereekb.gae.server.event.model.shared.event.impl.ModelEventDataImpl;
import com.dereekb.gae.server.event.model.shared.event.impl.ModelEventImpl;
import com.dereekb.gae.server.event.model.shared.event.service.ModelEventKeyLoader;

/**
 * {@link ModelEventKeyLoader} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ModelEventLoaderImpl<T extends UniqueModel>
        implements ModelEventKeyLoader<T> {

	private ModelKeyListAccessorFactory<T> accessorFactory;

	public ModelEventLoaderImpl(ModelKeyListAccessorFactory<T> accessorFactory) {
		this.setAccessorFactory(accessorFactory);
	}

	public ModelKeyListAccessorFactory<T> getAccessorFactory() {
		return this.accessorFactory;
	}

	public void setAccessorFactory(ModelKeyListAccessorFactory<T> accessorFactory) {
		if (accessorFactory == null) {
			throw new IllegalArgumentException("accessorFactory cannot be null.");
		}

		this.accessorFactory = accessorFactory;
	}

	// MARK: ModelEventKeyLoader
	@Override
	public ModelEvent<T> loadModelEvent(ModelKeyEvent keyEvent) throws IllegalModelTypeException {
		ModelKeyEventData data = keyEvent.getEventData();
		List<ModelKey> keys = data.getEventModelKeys();

		ModelKeyListAccessor<T> accessor = this.accessorFactory.createAccessor(keys);

		EventType eventType = keyEvent.getEventType();
		ModelEventData<T> modelEventData = new ModelEventDataImpl<T>(accessor);

		return new ModelEventImpl<T>(eventType, modelEventData);
	}

	@Override
	public String toString() {
		return "ModelEventLoaderImpl [accessorFactory=" + this.accessorFactory + "]";
	}

}
