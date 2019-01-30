package com.dereekb.gae.server.datastore.models.keys.accessor.task;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;

/**
 * {@link ModelKeyListAccessorTransformTask} delegate used for converting from
 * one {@link ModelKeyListAccessor} to another {@link ModelKeyListAccessor}.
 * 
 * @author dereekb
 *
 */
public interface ModelKeyListAccessorTransformTaskDelegate<I extends UniqueModel, O extends UniqueModel> {

	/**
	 * Converts a {@link ModelKeyListAccessor} of one type to another.
	 * 
	 * @param input
	 *            {@link ModelKeyListAccessor}. Never {@code null}.
	 * @return {@link ModelKeyListAccessor}. Never {@code null}.
	 */
	public ModelKeyListAccessor<O> convertListAccessor(ModelKeyListAccessor<I> input);

}
