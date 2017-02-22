package com.dereekb.gae.model.extension.search.query.service;

import com.dereekb.gae.utilities.query.exception.IllegalQueryArgumentException;

/**
 * Service for performing model queries.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ModelQueryService<T> {

	/**
	 * Performs a query for models.
	 * 
	 * @param request
	 *            {@link ModelQueryRequest}. Never {@code null}.
	 * @return {@link ModelQueryResponse}. Never {@code null}.
	 * 
	 * @throws IllegalQueryArgumentException
	 *             thrown if any query arguments are invalid.
	 */
	public ModelQueryResponse<T> queryModels(ModelQueryRequest request) throws IllegalQueryArgumentException;

}
