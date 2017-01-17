package com.dereekb.gae.model.extension.search.query.service;


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
