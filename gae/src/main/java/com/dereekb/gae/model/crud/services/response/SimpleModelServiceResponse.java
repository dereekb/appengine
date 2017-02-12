package com.dereekb.gae.model.crud.services.response;

import java.util.Collection;

/**
 * Service response that returns models.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface SimpleModelServiceResponse<T>
        extends SimpleServiceResponse {

	/**
	 * Returns all successful models.
	 * 
	 * @return {@link Collection}. Never {@code null}.
	 */
	public Collection<T> getModels();

}
