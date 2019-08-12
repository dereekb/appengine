package com.dereekb.gae.model.extension.search.document.service.impl;

import com.dereekb.gae.model.extension.search.document.service.ModelSearchServiceRequest;
import com.dereekb.gae.server.search.query.SearchServiceQueryExpression;
import com.dereekb.gae.server.search.request.SearchServiceQueryOptions;
import com.dereekb.gae.server.search.request.SearchServiceQueryRequest;

/**
 * {@link ModelSearchServiceRequest} implementation.
 *
 * @author dereekb
 *
 */
public class ModelSearchServiceRequestImpl
        implements ModelSearchServiceRequest {

	private String modelType;
	private SearchServiceQueryRequest queryRequest;

	public ModelSearchServiceRequestImpl(String modelType, SearchServiceQueryRequest queryRequest) {
		super();
		this.setModelType(modelType);
		this.setQueryRequest(queryRequest);
	}

	@Override
	public String getModelType() {
		return this.modelType;
	}

	public void setModelType(String modelType) {
		if (modelType == null) {
			throw new IllegalArgumentException("modelType cannot be null.");
		}

		this.modelType = modelType;
	}

	public SearchServiceQueryRequest getQueryRequest() {
		return this.queryRequest;
	}

	public void setQueryRequest(SearchServiceQueryRequest queryRequest) {
		if (queryRequest == null) {
			throw new IllegalArgumentException("queryRequest cannot be null.");
		}

		this.queryRequest = queryRequest;
	}

	// MARK: ModelSearchServiceRequest
	@Override
	public SearchServiceQueryOptions getSearchOptions() {
		return this.queryRequest.getSearchOptions();
	}

	@Override
	public SearchServiceQueryExpression getExpression() {
		return this.queryRequest.getExpression();
	}

	@Override
	public String getIndexName() {
		return this.queryRequest.getIndexName();
	}

	@Override
	public String toString() {
		return "ModelSearchServiceRequestImpl [modelType=" + this.modelType + ", queryRequest=" + this.queryRequest
		        + "]";
	}

}
