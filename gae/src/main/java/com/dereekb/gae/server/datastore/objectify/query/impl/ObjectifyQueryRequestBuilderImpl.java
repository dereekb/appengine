package com.dereekb.gae.server.datastore.objectify.query.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.components.query.ObjectifyEntityQueryService;
import com.dereekb.gae.server.datastore.objectify.query.ExecutableObjectifyQuery;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryFilter;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestBuilder;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifySimpleQueryFilter;
import com.dereekb.gae.server.datastore.objectify.query.order.ObjectifyQueryOrdering;
import com.dereekb.gae.server.datastore.objectify.query.order.ObjectifyQueryOrderingChain;
import com.dereekb.gae.server.datastore.objectify.query.order.impl.ObjectifyQueryOrderingChainImpl;
import com.dereekb.gae.utilities.collections.IteratorUtility;
import com.google.appengine.api.datastore.Cursor;

/**
 * {@link ObjectifyQueryRequestBuilder} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ObjectifyQueryRequestBuilderImpl<T extends ObjectifyModel<T>>
        implements ObjectifyQueryRequestBuilder<T> {

	private static final Integer DEFAULT_LIMIT = 20;

	private final ObjectifyEntityQueryService<T> queryService;

	private List<ObjectifyQueryFilter> queryFilters;
	private List<ObjectifySimpleQueryFilter<T>> simpleQueryFilters;
	private ObjectifyQueryOrderingChain resultsOrdering;

	private Integer limit = DEFAULT_LIMIT;
	private Cursor cursor = null;
	private boolean allowCache = true;

	public ObjectifyQueryRequestBuilderImpl(ObjectifyEntityQueryService<T> queryService) throws IllegalArgumentException {
		if (queryService == null) {
			throw new IllegalArgumentException("Service cannot be null.");
		}

		this.queryService = queryService;
		this.setQueryFilters(null);
		this.setSimpleQueryFilters(null);
	}

	@Override
	public List<ObjectifyQueryFilter> getQueryFilters() {
		return this.queryFilters;
	}

	@Override
	public void addQueryFilter(ObjectifyQueryFilter filter) {
		if (filter != null) {
			this.queryFilters.add(filter);
		}
	}

	@Override
	public void setQueryFilters(Iterable<ObjectifyQueryFilter> filters) {
		List<ObjectifyQueryFilter> queryFilters = IteratorUtility.iterableToList(filters);
		this.setQueryFilters(queryFilters);
	}

	public void setQueryFilters(List<ObjectifyQueryFilter> queryFilters) {
		this.queryFilters = new ArrayList<ObjectifyQueryFilter>();

		if (queryFilters != null) {
			this.queryFilters.addAll(queryFilters);
		}
	}

	@Override
	public List<ObjectifySimpleQueryFilter<T>> getSimpleQueryFilters() {
		return this.simpleQueryFilters;
	}

	@Override
	public void addSimpleQueryFilter(ObjectifySimpleQueryFilter<T> filter) {
		if (filter != null) {
			this.simpleQueryFilters.add(filter);
		}
	}

	@Override
	public void setSimpleQueryFilters(Iterable<ObjectifySimpleQueryFilter<T>> filters) {
		List<ObjectifySimpleQueryFilter<T>> queryFilters = IteratorUtility.iterableToList(filters);
		this.setSimpleQueryFilters(queryFilters);
	}

	public void setSimpleQueryFilters(List<ObjectifySimpleQueryFilter<T>> simpleQueryFilters) {
		this.simpleQueryFilters = new ArrayList<ObjectifySimpleQueryFilter<T>>();

		if (simpleQueryFilters != null) {
			this.simpleQueryFilters.addAll(simpleQueryFilters);
		}
	}

	@Override
	public ObjectifyQueryOrderingChain getResultsOrdering() {
		return this.resultsOrdering;
	}

	public void setResultsOrdering(ObjectifyQueryOrdering ordering) {
		if (ordering == null) {
			this.resultsOrdering = null;
		} else {
			ObjectifyQueryOrderingChain chain = new ObjectifyQueryOrderingChainImpl(ordering);
			this.setResultsOrdering(chain);
		}
	}

	@Override
	public void setResultsOrdering(ObjectifyQueryOrderingChain orderingChain) {
		this.resultsOrdering = orderingChain;
	}

	@Override
	public void addResultsOrdering(ObjectifyQueryOrdering ordering) {
		if (this.resultsOrdering != null) {
			this.resultsOrdering.chain(ordering);
		} else {
			this.setResultsOrdering(ordering);
		}
	}

	@Override
	public Integer getLimit() {
		return this.limit;
	}

	@Override
	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	@Override
	public Cursor getCursor() {
		return this.cursor;
	}

	@Override
	public void setCursor(Cursor cursor) {
		this.cursor = cursor;
	}

	@Override
	public boolean allowCache() {
		return this.allowCache;
	}

	@Override
	public void setAllowCache(boolean allowCache) {
		this.allowCache = allowCache;
	}

	@Override
	public ObjectifyQueryRequestBuilder<T> copyBuilder() {
		ObjectifyQueryRequestBuilderImpl<T> builder = new ObjectifyQueryRequestBuilderImpl<T>(this.queryService);

		builder.setQueryFilters(this.queryFilters);
		builder.setSimpleQueryFilters(this.simpleQueryFilters);
		builder.setResultsOrdering(this.resultsOrdering);

		builder.setLimit(this.limit);
		builder.setCursor(this.cursor);
		builder.setAllowCache(this.allowCache);

		return builder;
	}

	@Override
	public ExecutableObjectifyQuery<T> buildExecutableQuery() {
		ObjectifyQueryRequestBuilder<T> copy = this.copyBuilder();
		return new ExecutableObjectifyQueryImpl<T>(this.queryService, copy);
	}

	@Override
	public String toString() {
		return "ObjectifyQueryRequestBuilderImpl [queryService=" + this.queryService + ", queryFilters="
		        + this.queryFilters + ", simpleQueryFilters=" + this.simpleQueryFilters + ", resultsOrdering="
		        + this.resultsOrdering + ", limit=" + this.limit + ", cursor=" + this.cursor + ", allowCache="
		        + this.allowCache + "]";
	}

}
