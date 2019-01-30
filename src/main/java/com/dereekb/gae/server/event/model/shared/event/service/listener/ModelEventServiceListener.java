package com.dereekb.gae.server.event.model.shared.event.service.listener;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.event.model.shared.event.ModelEvent;

/**
 * Event listener that listens for {@link ModelEvent} instances.
 * <p>
 * Is usually used within a {@link ModelKeyEventServiceListener}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ModelEventServiceListener<T extends UniqueModel> {

	/**
	 * Handles the input event.
	 *
	 * @param event
	 *            {@link ModelEvent}. Never {@code null}.
	 */
	public void handleModelEvent(ModelEvent<T> event);

}
