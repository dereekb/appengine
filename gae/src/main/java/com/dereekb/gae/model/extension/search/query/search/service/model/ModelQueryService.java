package com.dereekb.gae.model.extension.search.query.search.service.model;



/**
 * Service for reading model instances using a custom query.
 *
 * @author dereekb
 *
 * @param <T>
 *            Model type.
 * @param <Q>
 *            Query type.
 */
public interface ModelQueryService<T, Q> {

	public ModelQueryResponse<T> queryModels(Q query);

}
