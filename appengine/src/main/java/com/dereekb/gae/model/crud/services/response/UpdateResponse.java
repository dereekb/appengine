package com.dereekb.gae.model.crud.services.response;

import java.util.Collection;

import com.dereekb.gae.model.crud.services.response.pair.InvalidTemplatePair;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Update service response.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface UpdateResponse<T extends UniqueModel>
        extends ServiceResponse, SimpleUpdateResponse<T> {

	/**
	 * {@inheritDoc}
	 * 
	 * Returns a collections of {@link InvalidTemplatePair} instead.
	 */
	@Override
	public Collection<? extends InvalidTemplatePair<T>> getUpdateFailures();

}
