package com.dereekb.gae.server.event.model.shared.event;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.event.event.Event;

/**
 * {@link Event} with {@link ModelEventData}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ModelEvent<T extends UniqueModel>
        extends ModelKeyEvent {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ModelEventData<T> getEventData();

}
