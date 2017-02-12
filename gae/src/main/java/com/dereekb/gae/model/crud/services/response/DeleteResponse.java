package com.dereekb.gae.model.crud.services.response;

import java.util.Collection;

import com.dereekb.gae.model.crud.services.components.DeleteService;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Delete response for a {@link DeleteService}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 * 
 * @see DeleteRequest
 */
public interface DeleteResponse<T> {

	/**
	 * Returns the models that were deleted.
	 * 
	 * @return {@link Collection}. Never {@code null}.
	 */
	public Collection<T> getDeletedModels();

	/**
	 * Returns a collection of {@link ModelKey} instances for elements
	 * that were filtered out.
	 * 
	 * @return {@link Collection}. Never {@code null}.
	 */
	public Collection<ModelKey> getFiltered();

	/**
	 * Returns a collection of {@link ModelKey} instances for elements
	 * that were not available, generally meaning they've been deleted
	 * already.
	 * 
	 * @return {@link Collection}. Never {@code null}.
	 */
	public Collection<ModelKey> getUnavailable();

}
