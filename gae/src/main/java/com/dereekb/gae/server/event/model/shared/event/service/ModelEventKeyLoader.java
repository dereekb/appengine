package com.dereekb.gae.server.event.model.shared.event.service;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.exception.IllegalModelTypeException;
import com.dereekb.gae.server.event.model.shared.event.ModelEvent;
import com.dereekb.gae.server.event.model.shared.event.ModelKeyEvent;

/**
 * Interface used for upgrading an {@link ModelKeyEvent} to a
 * {@link ModelEvent}.
 *
 * @author dereekb
 *
 */
public interface ModelEventKeyLoader<T extends UniqueModel> {

	/**
	 * Loads a new {@link ModelEvent} using the input {@link ModelKeyEvent}.
	 *
	 * @param keyEvent
	 *            {@link ModelKeyEvent}. never {@code null}.
	 * @return {@link ModelEvent}. Never {@code null}.
	 * @throws IllegalModelTypeException
	 *             if the model does not exist.
	 */
	public ModelEvent<T> loadModelEvent(ModelKeyEvent keyEvent) throws IllegalModelTypeException;

}
