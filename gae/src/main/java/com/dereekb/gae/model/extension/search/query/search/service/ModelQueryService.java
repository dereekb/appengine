package com.dereekb.gae.model.extension.search.query.search.service;


/**
 * Service for performing model queries
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ModelQueryService<T> {

	public ModelQueryResponse<T> queryModels(ModelQueryRequest request);

}
