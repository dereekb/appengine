package com.dereekb.gae.server.datastore.objectify.query;

import java.util.List;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.query.order.ObjectifyQueryOrdering;
import com.google.appengine.api.datastore.Cursor;

/**
 * Utility wrapper for performing queries with Objectify.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ObjectifyQueryRequest<T extends ObjectifyModel<T>> {

	/**
	 * @return {@link List} of {@link ObjectifyQueryFilter} values. Never
	 *         {@code null}.
	 */
	public List<ObjectifyQueryFilter> getQueryFilters();

	/**
	 * @return {@link List} of {@link ObjectifySimpleQueryFilter} values. Never
	 *         {@code null}.
	 */
	public List<ObjectifySimpleQueryFilter<T>> getSimpleQueryFilters();

	public Iterable<ObjectifyQueryOrdering> getResultsOrdering();

	public Cursor getCursor();

	public Integer getLimit();

	public boolean allowCache();

}
