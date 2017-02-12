package com.dereekb.gae.model.crud.services.response;

import java.util.Collection;

import com.dereekb.gae.model.crud.services.response.pair.UpdateResponseFailurePair;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Update service response.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface UpdateResponse<T> {

	/**
	 * Returns a collection of models that were updated.s
	 * 
	 * @return {@link Collection}. Never {@code null}.
	 */
	public Collection<T> getUpdatedModels();

	/**
	 * Returns a collection of models that were unavailable for updating.
	 * 
	 * @return {@link Collection}. Never {@code null}.
	 */
	public Collection<ModelKey> getUnavailable();

	/**
	 * Returns a collection of pairs that failed to be updated.
	 * 
	 * @return {@link Collection}. Never {@code null}.
	 */
	public Collection<UpdateResponseFailurePair<T>> getFailed();

}
