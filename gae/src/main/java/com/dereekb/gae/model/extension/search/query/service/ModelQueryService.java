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

	public ModelQueryResponse<T> queryModels(ModelQueryRequest request) throws IllegalQueryArgumentException;

}
