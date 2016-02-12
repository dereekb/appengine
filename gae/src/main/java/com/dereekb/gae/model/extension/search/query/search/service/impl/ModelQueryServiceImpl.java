package com.dereekb.gae.model.extension.search.query.search.service.impl;

import com.dereekb.gae.model.extension.search.query.search.service.ModelQueryRequest;
import com.dereekb.gae.model.extension.search.query.search.service.ModelQueryResponse;
import com.dereekb.gae.model.extension.search.query.search.service.ModelQueryService;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;

/**
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ModelQueryServiceImpl<T extends ObjectifyModel<T>>
        implements ModelQueryService<T> {

	private ObjectifyRegistry<T> registry;

	public ObjectifyRegistry<T> getRegistry() {
		return this.registry;
	}

	public void setRegistry(ObjectifyRegistry<T> registry) {
		this.registry = registry;
	}

	// MARK: ModelQueryService
	@Override
	public ModelQueryResponse<T> queryModels(ModelQueryRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	private class ResponseImpl
	        implements ModelQueryResponse<T> {

	}

}
