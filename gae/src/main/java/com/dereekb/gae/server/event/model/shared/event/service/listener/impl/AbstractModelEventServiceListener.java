package com.dereekb.gae.server.event.model.shared.event.service.listener.impl;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.event.model.shared.event.ModelEvent;
import com.dereekb.gae.server.event.model.shared.event.ModelKeyEvent;
import com.dereekb.gae.server.event.model.shared.event.service.ModelEventKeyLoader;
import com.dereekb.gae.server.event.model.shared.event.service.listener.ModelEventServiceListener;
import com.dereekb.gae.server.event.model.shared.event.service.listener.ModelKeyEventServiceListener;

/**
 * Abstract {@link ModelEventServiceListener} implementation that also
 * implements {@link ModelKeyEventServiceListener} and converts input
 * {@link ModelKeyEvent} values to {@link ModelEvent}.
 *
 * @author dereekb
 *
 */
public abstract class AbstractModelEventServiceListener<T extends UniqueModel>
        implements ModelEventServiceListener<T>, ModelKeyEventServiceListener {

	private ModelEventKeyLoader<T> loader;

	public AbstractModelEventServiceListener(ModelEventKeyLoader<T> loader) {
		this.setLoader(loader);
	}

	public ModelEventKeyLoader<T> getLoader() {
		return this.loader;
	}

	public void setLoader(ModelEventKeyLoader<T> loader) {
		if (loader == null) {
			throw new IllegalArgumentException("loader cannot be null.");
		}

		this.loader = loader;
	}

	// MARK: ModelEventServiceListener
	@SuppressWarnings("unchecked")
	@Override
	public void handleModelKeyEvent(ModelKeyEvent event) {
		ModelEvent<T> modelEvent = null;

		if (ModelEvent.class.isAssignableFrom(event.getClass())) {
			modelEvent = (ModelEvent<T>) event;
		} else {
			modelEvent = this.loader.loadModelEvent(modelEvent);
		}

		this.handleModelEvent(modelEvent);
	}

	@Override
	public abstract void handleModelEvent(ModelEvent<T> event);

}
