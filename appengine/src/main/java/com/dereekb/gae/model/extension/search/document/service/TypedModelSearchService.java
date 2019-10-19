package com.dereekb.gae.model.extension.search.document.service;

import com.dereekb.gae.utilities.query.exception.IllegalQueryArgumentException;

/**
 * {@link SearchService} for a specific model.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface TypedModelSearchService<T> {

	/**
	 * Performs a search for models.
	 *
	 * @param request
	 *            {@link TypedModelSearchServiceRequest}. Never {@code null}.
	 * @return {@link TypedModelSearchServiceResponse}. Never {@code null}.
	 *
	 * @throws IllegalQueryArgumentException
	 *             thrown if any query arguments are invalid.
	 */
	public TypedModelSearchServiceResponse<T> searchModels(TypedModelSearchServiceRequest request)
	        throws IllegalQueryArgumentException;

}
