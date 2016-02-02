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
public interface ConfiguredObjectifyQuery<T extends ObjectifyModel<T>> {

	public List<ObjectifyQueryFilter> getQueryFilters();

	public List<ObjectifySimpleQueryFilter<T>> getSimpleQueryFilters();

	public Iterable<ObjectifyQueryOrdering> getResultsOrdering();

	public Cursor getCursor();

	public Integer getLimit();

	public boolean cacheIsDisabled();

}
