package com.dereekb.gae.model.extension.links.crud.task;

import java.util.List;

import com.dereekb.gae.model.crud.pairs.CreatePair;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * {@link LinkCreateTaskImpl} delegate.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface LinkCreateTaskDelegate<T extends UniqueModel> {

	/**
	 * Creates new {@link LinkCreateTaskPair} values for the input create pairs.
	 * 
	 * @param pairs
	 *            {@link Iterable}. Never {@code null}.
	 * @return {@link List}. Never {@code null}.
	 */
	public List<LinkCreateTaskPair<T>> buildTaskPairs(Iterable<CreatePair<T>> pairs);

}
