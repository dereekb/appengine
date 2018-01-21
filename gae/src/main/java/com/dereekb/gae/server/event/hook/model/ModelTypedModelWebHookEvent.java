package com.dereekb.gae.server.event.hook.model;

import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * {@link TypedModelWebHookEvent} that contains models.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ModelTypedModelWebHookEvent<T extends UniqueModel>
        extends TypedModelWebHookEvent {

	/**
	 * Returns the list of models for this event.
	 *
	 * @return {@link List}. Never {@code null}.
	 */
	public List<T> getModels();

}
