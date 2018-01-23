package com.dereekb.gae.server.event.model.shared.event;

import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * {@link ModelKeyEventData} that provides access to models.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ModelEventData<T extends UniqueModel>
        extends ModelKeyEventData {

	/**
	 * Returns a list of all models.
	 *
	 * @return {@link List}. Never {@code null}.
	 */
	public List<T> getEventModels();

}
