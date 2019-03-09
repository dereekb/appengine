package com.dereekb.gae.model.crud.services.response;

import java.util.Collection;

/**
 * Simple read response.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface SimpleReadResponse<T>
        extends SimpleModelServiceResponse<T> {

	/**
	 * {@inheritDoc}
	 * 
	 * This is the collection of models read.
	 */
	@Override
	public Collection<T> getModels();

}
