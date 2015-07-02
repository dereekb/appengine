package com.dereekb.gae.server.datastore.objectify.query;

import java.util.ArrayList;
import java.util.List;

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
 */
public final class ObjectifyQuery<T> {

	private static final Integer DEFAULT_LIMIT = 20;

	private final Class<T> type;
	private boolean disableCache = false;

	private Integer limit = DEFAULT_LIMIT;
	private Cursor cursor = null;
	private ObjectifyQueryOrdering resultsOrdering;

	private final List<ObjectifyQueryFilter> queryFilters = new ArrayList<ObjectifyQueryFilter>();
	private final List<ObjectifySimpleQueryFilter<T>> simpleQueryFilters = new ArrayList<ObjectifySimpleQueryFilter<T>>();

	public ObjectifyQuery(Class<T> type) {
		this.type = type;
	}

	public ObjectifyQuery(Class<T> type,
	        List<ObjectifyQueryFilter> queryFilters,
	        List<ObjectifySimpleQueryFilter<T>> simpleQueryFilters) {
		this.type = type;
	}

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

		for (ObjectifySimpleQueryFilter<T> filter : this.simpleQueryFilters) {
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

	public SimpleQuery<T> executeQuery(Objectify objectify) {
		Query<T> query = objectify.load().type(this.type);

		query = this.applyLimit(query);
		query = this.applyCursor(query);

		Query<T> filteredQuery = this.applyFilters(query);
		filteredQuery = this.applyResultsOrdering(filteredQuery);

		SimpleQuery<T> simpleFilteredQuery = this.applySimpleFilters(filteredQuery);
		return simpleFilteredQuery;
	}

	public List<ObjectifyQueryFilter> getQueryFilters() {
		return this.queryFilters;
	}

	public void addFilter(ObjectifyQueryFilter filter) {
		this.queryFilters.add(filter);
	}

	public List<ObjectifySimpleQueryFilter<T>> getSimpleQueryFilters() {
		return this.simpleQueryFilters;
	}

	public void addFilter(ObjectifySimpleQueryFilter<T> filter) {
		this.simpleQueryFilters.add(filter);
	}

	public ObjectifyQueryOrdering getResultsOrdering() {
		return this.resultsOrdering;
	}

	public void setResultsOrdering(ObjectifyQueryOrdering ordering) {
		this.resultsOrdering = ordering;
	}

	public Integer getLimit() {
		return this.limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public boolean isDisableCache() {
		return this.disableCache;
	}

	public void setDisableCache(boolean disableCache) {
		this.disableCache = disableCache;
	}

	public Cursor getCursor() {
		return this.cursor;
	}

	public void setCursor(Cursor cursor) {
		this.cursor = cursor;
	}

	public void setCursor(String encodedCursor) {
		this.cursor = Cursor.fromWebSafeString(encodedCursor);
	}

	@Override
	public String toString() {
		return "ObjectifyQuery [type=" + this.type + ", disableCache=" + this.disableCache + ", resultsOrdering="
		        + this.resultsOrdering + ", queryFilters=" + this.queryFilters + ", simpleQueryFilters=" + this.simpleQueryFilters
		        + "]";
	}

}
