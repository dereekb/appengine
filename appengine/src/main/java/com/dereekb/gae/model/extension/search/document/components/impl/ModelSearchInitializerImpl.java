package com.dereekb.gae.model.extension.search.document.components.impl;

import java.util.Map;

import com.dereekb.gae.model.extension.search.document.components.ModelSearch;
import com.dereekb.gae.model.extension.search.document.components.ModelSearchFactory;
import com.dereekb.gae.model.extension.search.document.components.ModelSearchInitializer;
import com.dereekb.gae.server.search.query.SearchServiceQueryExpression;
import com.dereekb.gae.server.search.request.SearchServiceQueryOptions;
import com.dereekb.gae.server.search.request.SearchServiceQueryRequest;
import com.dereekb.gae.server.search.request.impl.SearchServiceQueryRequestImpl;
import com.dereekb.gae.utilities.query.exception.IllegalQueryArgumentException;

/**
 * {@link ModelSearchInitializer} implementation that wraps a
 * {@link ModelSearchFactory}.
 *
 * @author dereekb
 *
 */
public class ModelSearchInitializerImpl
        implements ModelSearchInitializer<ModelSearch> {

	private ModelSearchFactory<ModelSearch> modelSearchFactory;

	public ModelSearchInitializerImpl(ModelSearchFactory<ModelSearch> modelSearchFactory) {
		super();
		this.setModelSearchFactory(modelSearchFactory);
	}

	public ModelSearchFactory<ModelSearch> getModelSearchFactory() {
		return this.modelSearchFactory;
	}

	public void setModelSearchFactory(ModelSearchFactory<ModelSearch> modelSearchFactory) {
		if (this.modelSearchFactory == null) {
			throw new IllegalArgumentException("modelSearchFactory cannot be null.");
		}

		this.modelSearchFactory = modelSearchFactory;
	}

	// MARK: ModelSearchInitializer
	@Override
	public SearchServiceQueryRequest initalizeSearchRequest(String index,
	                                                        SearchServiceQueryOptions searchOptions,
	                                                        Map<String, String> parameters)
	        throws IllegalQueryArgumentException {
		SearchServiceQueryExpression expression = this.modelSearchFactory.makeSearch(parameters);
		SearchServiceQueryRequest queryRequest = new SearchServiceQueryRequestImpl(index, searchOptions, expression);
		return queryRequest;
	}

	@Override
	public String toString() {
		return "ModelSearchInitializerImpl [modelSearchFactory=" + this.modelSearchFactory + "]";
	}

}
