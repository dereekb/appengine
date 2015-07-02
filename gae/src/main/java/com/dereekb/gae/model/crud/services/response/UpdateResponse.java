package com.dereekb.gae.model.crud.services.response;

import java.util.Collection;

import com.dereekb.gae.model.crud.services.response.pair.UpdateResponseFailurePair;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;


public interface UpdateResponse<T> {

	/**
	 * @return Returns a collection of models that were updated.
	 */
	public Collection<T> getUpdatedModels();

	/**
	 * @return Returns a collection of {@link ModelKey} instances for elements
	 *         that failed to be updated.
	 */
	public Collection<UpdateResponseFailurePair<T>> getFailed();

}
