package com.dereekb.gae.server.datastore.objectify.query.impl;

import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.components.query.ObjectifyEntityQueryService;
import com.dereekb.gae.server.datastore.objectify.query.ExecutableObjectifyQuery;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryFilter;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryModelResponse;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequest;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestOptions;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifySimpleQueryFilter;
import com.dereekb.gae.server.datastore.objectify.query.order.ObjectifyQueryOrdering;
import com.dereekb.gae.utilities.collections.chain.Chain;
import com.dereekb.gae.utilities.model.search.exception.NoSearchCursorException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.SimpleQuery;

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

	private ObjectifyQueryModelResponse<T> response;

	public ExecutableObjectifyQueryImpl(ObjectifyEntityQueryService<T> service,
	        ObjectifyQueryRequest<T> request,
	        ObjectifyQueryRequestOptions options) throws IllegalArgumentException {
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

	public ObjectifyQueryModelResponse<T> getResponse() {
		if (this.response == null) {
			this.response = this.service.query(this.request);
		}

		return this.response;
	}

	@Override
	public Integer getResultCount() {
		return this.getResponse().getResultCount();
	}

	@Override
	public Cursor getCursor() throws NoSearchCursorException {
		return this.getResponse().getCursor();
	}

	@Override
	public boolean hasResults() {
		return this.getResponse().hasResults();
	}

	@Override
	public SimpleQuery<T> getQuery() {
		return this.getResponse().getQuery();
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
	public QueryResultIterator<T> queryModelsIterator() {
		return this.getResponse().queryModelsIterator();
	}

	@Override
	public QueryResultIterator<Key<T>> queryObjectifyKeyIterator() {
		return this.getResponse().queryObjectifyKeyIterator();
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
	public Chain<ObjectifyQueryOrdering> getResultsOrdering() {
		return this.request.getResultsOrdering();
	}

	@Override
	public String toString() {
		return "ExecutableObjectifyQueryImpl [request=" + this.request + ", options=" + this.options + ", service="
		        + this.service + ", response=" + this.response + "]";
	}

}
