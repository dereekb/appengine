package com.dereekb.gae.model.crud.services.response;

import java.util.Collection;

/**
 * Simple delete response.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface SimpleDeleteResponse<T>
        extends SimpleModelServiceResponse<T> {

	/**
	 * {@inheritDoc}
	 * 
	 * This is the collection of models successfully deleted.
	 */
	@Override
	public Collection<T> getModels();

}
