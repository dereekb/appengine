package com.dereekb.gae.model.crud.services.response;

import java.util.Collection;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

public interface DeleteResponse<T> {

	/**
	 * @return Returns the models that were deleted.
	 */
	public Collection<T> getDeletedModels();

	/**
	 * @return Returns a collection of {@link ModelKey} instances for elements
	 *         that were filtered out.
	 */
	public Collection<ModelKey> getFiltered();

	/**
	 * @return Returns a collection of {@link ModelKey} instances for elements
	 *         that were not available, generally meaning they've been deleted
	 *         already.
	 */
	public Collection<ModelKey> getUnavailable();

}
