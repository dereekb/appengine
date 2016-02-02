package com.dereekb.gae.server.datastore.objectify.query;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.query.order.ObjectifyQueryOrdering;
import com.google.appengine.api.datastore.Cursor;

/**
 * Used for building {@link ExecutableObjectifyQuery} instances.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ObjectifyQueryRequestBuilder<T extends ObjectifyModel<T>>
        extends ObjectifyQueryRequest<T> {

	public void setAllowCache(boolean allowCache);

	public void setLimit(Integer limit);

	public void setCursor(Cursor cursor);

	public void setResultsOrdering(Iterable<ObjectifyQueryOrdering> ordering);

	public void addQueryFilter(ObjectifyQueryFilter filter);

	public void setQueryFilters(Iterable<ObjectifyQueryFilter> filters);

	public void addSimpleQueryFilter(ObjectifySimpleQueryFilter<T> filter);

	public void setSimpleQueryFilters(Iterable<ObjectifySimpleQueryFilter<T>> filters);

	public ObjectifyQueryRequestBuilder<T> copyBuilder();

	public ExecutableObjectifyQuery<T> buildExecutableQuery();

}
