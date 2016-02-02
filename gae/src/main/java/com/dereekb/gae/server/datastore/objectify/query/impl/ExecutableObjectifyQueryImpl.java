package com.dereekb.gae.server.datastore.objectify.query.impl;

import java.util.List;
import java.util.Set;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.components.query.ObjectifyEntityQueryService;
import com.dereekb.gae.server.datastore.objectify.query.ExecutableObjectifyQuery;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryFilter;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequest;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryResponse;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifySimpleQueryFilter;
import com.dereekb.gae.server.datastore.objectify.query.order.ObjectifyQueryOrdering;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
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

	private ObjectifyEntityQueryService<T> service;
	private ObjectifyQueryRequest<T> request;

	private ObjectifyQueryResponse<T> response;

	public ExecutableObjectifyQueryImpl(ObjectifyEntityQueryService<T> service, ObjectifyQueryRequest<T> query)
	        throws IllegalArgumentException {
		this.setService(service);
		this.setRequest(query);
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
	public Set<Key<T>> queryObjectifyKeys() {
		return this.getResponse().queryObjectifyKeys();
	}

	@Override
	public Set<ModelKey> queryModelKeys() {
		return this.getResponse().queryModelKeys();
	}

	@Override
	public QueryResultIterator<T> queryIterator() {
		return this.getResponse().queryIterator();
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
	public Iterable<ObjectifyQueryOrdering> getResultsOrdering() {
		return this.request.getResultsOrdering();
	}

	@Override
	public Cursor getCursor() {
		return this.request.getCursor();
	}

	@Override
	public Integer getLimit() {
		return this.request.getLimit();
	}

	@Override
	public boolean allowCache() {
		return this.request.allowCache();
	}

	@Override
	public String toString() {
		return "ExecutableObjectifyQueryImpl [service=" + this.service + ", query=" + this.request + "]";
	}

}
