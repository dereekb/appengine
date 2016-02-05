package com.dereekb.gae.server.datastore.objectify.query.impl;

import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.components.query.ObjectifyEntityQueryService;
import com.dereekb.gae.server.datastore.objectify.query.ExecutableObjectifyQuery;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryFilter;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequest;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestOptions;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryResponse;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifySimpleQueryFilter;
import com.dereekb.gae.server.datastore.objectify.query.order.ObjectifyQueryOrderingChain;
import com.google.appengine.api.datastore.QueryResultIterable;
import com.googlecode.objectify.Key;

/**
 * {@link ExecutableObjectifyQuery} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ExecutableObjectifyQueryImpl<T extends ObjectifyModel<T>>
        implements ExecutableObjectifyQuery<T> {

	private ObjectifyQueryRequest<T> request;
	private ObjectifyQueryRequestOptions options;
	private ObjectifyEntityQueryService<T> service;

	private ObjectifyQueryResponse<T> response;

	public ExecutableObjectifyQueryImpl(ObjectifyEntityQueryService<T> service,
	        ObjectifyQueryRequest<T> request,
	        ObjectifyQueryRequestOptions options)
	        throws IllegalArgumentException {
		this.setService(service);
		this.setRequest(request);
		this.setOptions(options);
    }

	public ObjectifyEntityQueryService<T> getService() {
		return this.service;
	}

	public void setService(ObjectifyEntityQueryService<T> service) throws IllegalArgumentException {
		if (service == null) {
			throw new IllegalArgumentException("Service cannot be null.");
		}

		this.service = service;
	}

	@Override
	public ObjectifyQueryRequestOptions getOptions() {
		return this.options;
	}

	public void setOptions(ObjectifyQueryRequestOptions options) {
		this.options = options;
	}

	// MARK: ObjectifyQueryResponse
	public ObjectifyQueryRequest<T> getRequest() {
		return this.request;
	}

	public void setRequest(ObjectifyQueryRequest<T> request) {
		if (request == null) {
			throw new IllegalArgumentException("Query cannot be null.");
		}

		this.request = request;
	}

	public ObjectifyQueryResponse<T> getResponse() {
    	if (this.response == null) {
			this.response = this.service.query(this.request);
    	}

    	return this.response;
    }

	@Override
	public List<T> queryModels() {
		return this.getResponse().queryModels();
	}

	@Override
	public List<Key<T>> queryObjectifyKeys() {
		return this.getResponse().queryObjectifyKeys();
	}

	@Override
	public List<ModelKey> queryModelKeys() {
		return this.getResponse().queryModelKeys();
	}

	@Override
	public QueryResultIterable<T> queryModelsIterable() {
		return this.getResponse().queryModelsIterable();
	}

	@Override
	public QueryResultIterable<Key<T>> queryObjectifyKeyIterable() {
		return this.getResponse().queryObjectifyKeyIterable();
	}

	// MARK: ObjectifyQueryRequest
	@Override
	public List<ObjectifyQueryFilter> getQueryFilters() {
		return this.request.getQueryFilters();
	}

	@Override
	public List<ObjectifySimpleQueryFilter<T>> getSimpleQueryFilters() {
		return this.request.getSimpleQueryFilters();
	}

	@Override
	public ObjectifyQueryOrderingChain getResultsOrdering() {
		return this.request.getResultsOrdering();
	}

	@Override
	public String toString() {
		return "ExecutableObjectifyQueryImpl [request=" + this.request + ", options=" + this.options + ", service="
		        + this.service + ", response=" + this.response + "]";
	}

}
