package com.dereekb.gae.model.crud.services.response;

import java.util.Collection;

import com.dereekb.gae.model.crud.services.response.pair.UpdateResponseFailurePair;

/**
 * Simple update response.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface SimpleUpdateResponse<T>
        extends SimpleModelServiceResponse<T> {

	/**
	 * {@inheritDoc}
	 * 
	 * This is the collection of models successfully updated.
	 */
	@Override
	public Collection<T> getModels();

	/**
	 * Returns a collection of pairs that failed to be updated.
	 * 
	 * @return {@link Collection}. Never {@code null}.
	 */
	public Collection<UpdateResponseFailurePair<T>> getFailurePairs();

}
