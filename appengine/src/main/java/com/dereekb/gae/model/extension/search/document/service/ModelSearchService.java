package com.dereekb.gae.model.extension.search.document.service;

import com.dereekb.gae.utilities.query.exception.IllegalQueryArgumentException;

/**
 * {@link SearchService} for a specific model.
 *
 * @author dereekb
 *
 */
public interface ModelSearchService {

	/**
	 * Performs a search for model keys.
	 *
	 * @param request
	 *            {@link ModelSearchServiceRequest}. Never {@code null}.
	 * @return {@link ModelSearchServiceResponse}. Never {@code null}.
	 *
	 * @throws IllegalQueryArgumentException
	 *             thrown if any query arguments are invalid.
	 */
	public ModelSearchServiceResponse searchModels(ModelSearchServiceRequest request);

}
