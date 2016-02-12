package com.dereekb.gae.model.extension.search.query.search.service;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;

/**
 * Service for performing model queries
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ModelQueryService<T extends ObjectifyModel<T>> {

	public ModelQueryResponse<T> queryModels(ModelQueryRequest request);

}
