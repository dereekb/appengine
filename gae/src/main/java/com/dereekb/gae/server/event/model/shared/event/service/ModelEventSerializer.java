package com.dereekb.gae.server.event.model.shared.event.service;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.event.model.shared.event.ModelEvent;

/**
 * Used for serializing an event from JSON and vice-versa.
 *
 * @author dereekb
 *
 */
public interface ModelEventSerializer<T extends UniqueModel> {

	/**
	 * Serializes the event from the input JSON.
	 *
	 * @param eventJson
	 * @return {@link ModelEvent}.
	 */
	public ModelEvent<T> serializeEvent(String eventJson);

}
