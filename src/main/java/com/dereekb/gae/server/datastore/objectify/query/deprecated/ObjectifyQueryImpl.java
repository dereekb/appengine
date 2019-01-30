package com.dereekb.gae.server.datastore.objectify.query.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQuery;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryFilter;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryOrdering;
import com.google.appengine.api.datastore.Cursor;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.cmd.Query;
import com.googlecode.objectify.cmd.SimpleQuery;

/**
 * Utility wrapper for performing queries with Objectify.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
@Deprecated
public class ObjectifyQueryImpl<T>
        implements ObjectifyQuery<T> {

	private static final Integer DEFAULT_LIMIT = 20;

	private final Class<T> type;
	private boolean disableCache = false;

	private Integer limit = DEFAULT_LIMIT;
	private Cursor cursor = null;
	private ObjectifyQueryOrdering resultsOrdering;

	private final List<ObjectifyQueryFilter> queryFilters = new ArrayList<ObjectifyQueryFilter>();
	private final List<ObjectifyKeyInSetFilter<T>> simpleQueryFilters = new ArrayList<ObjectifyKeyInSetFilter<T>>();

	public ObjectifyQueryImpl(Class<T> type) {
		this.type = type;
	}

	public ObjectifyQueryImpl(Class<T> type,
	                      List<ObjectifyQueryFilter> queryFilters,
	                      List<ObjectifyKeyInSetFilter<T>> simpleQueryFilters) {
		this.type = type;
	}

	@Override
	public List<ObjectifyQueryFilter> getQueryFilters() {
		return this.queryFilters;
	}

	public void addFilter(ObjectifyQueryFilter filter) {
		this.queryFilters.add(filter);
	}

	@Override
	public List<ObjectifyKeyInSetFilter<T>> getSimpleQueryFilters() {
		return this.simpleQueryFilters;
	}

	public void addFilter(ObjectifyKeyInSetFilter<T> filter) {
		this.simpleQueryFilters.add(filter);
	}

	@Override
	public ObjectifyQueryOrdering getResultsOrdering() {
		return this.resultsOrdering;
	}

	@Override
    public void setResultsOrdering(ObjectifyQueryOrdering ordering) {
		this.resultsOrdering = ordering;
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
	public boolean isDisableCache() {
		return this.disableCache;
	}

	public void setDisableCache(boolean disableCache) {
		this.disableCache = disableCache;
	}

	@Override
	public Cursor getCursor() {
		return this.cursor;
	}

	@Override
    public void setCursor(Cursor cursor) {
		this.cursor = cursor;
	}

	public void setCursor(String encodedCursor) {
		this.cursor = Cursor.fromWebSafeString(encodedCursor);
	}

	// MARK: ObjectifyQuery
	@Override
    public Query<T> beginQuery(Objectify objectify) {
		Objectify cacheSet = objectify.cache(this.disableCache);
		Query<T> query = cacheSet.load().type(this.type);
		return query;
	}

	protected Query<T> applyFilters(Query<T> query) {
		Query<T> filteredQuery = query;

		for (ObjectifyQueryFilter filter : this.queryFilters) {
			filteredQuery = filter.filter(filteredQuery);
		}

		return filteredQuery;
	}

	protected SimpleQuery<T> applySimpleFilters(Query<T> query) {
		SimpleQuery<T> filteredQuery = query;

		for (ObjectifyKeyInSetFilter<T> filter : this.simpleQueryFilters) {
			filteredQuery = filter.filter(filteredQuery);
		}

		return filteredQuery;
	}

	protected Query<T> applyLimit(Query<T> query) {
		Query<T> limitedQuery = query;

		if (this.limit != null) {
			limitedQuery = limitedQuery.limit(this.limit);
		}

		return limitedQuery;
	}

	private Query<T> applyCursor(Query<T> query) {
		Query<T> newQuery = query;

		if (this.cursor != null) {
			newQuery = newQuery.startAt(this.cursor);
		}

		return newQuery;
	}

	protected Query<T> applyResultsOrdering(Query<T> query) {
		Query<T> orderedQuery = query;

		if (this.resultsOrdering != null) {
			for (ObjectifyQueryOrdering ordering : this.resultsOrdering) {
				if (ordering.isKeysOrdering()) {
					orderedQuery = orderedQuery.orderKey(this.resultsOrdering.isDescending());
				} else {
					String orderString = ordering.toString();
					orderedQuery = orderedQuery.order(orderString);
				}
			}
		}

		return orderedQuery;
	}

	@Override
    public SimpleQuery<T> executeQuery(Objectify objectify) {
		Query<T> query = objectify.load().type(this.type);

		query = this.applyLimit(query);
		query = this.applyCursor(query);

		Query<T> filteredQuery = this.applyFilters(query);
		filteredQuery = this.applyResultsOrdering(filteredQuery);

		SimpleQuery<T> simpleFilteredQuery = this.applySimpleFilters(filteredQuery);
		return simpleFilteredQuery;
	}

	@Override
	public String toString() {
		return "ObjectifyQuery [type=" + this.type + ", disableCache=" + this.disableCache + ", resultsOrdering="
		        + this.resultsOrdering + ", queryFilters=" + this.queryFilters + ", simpleQueryFilters="
		        + this.simpleQueryFilters + "]";
	}

}
