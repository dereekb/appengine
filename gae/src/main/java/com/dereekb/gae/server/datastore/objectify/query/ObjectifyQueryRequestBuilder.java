package com.dereekb.gae.server.datastore.objectify.query;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.query.exception.InvalidQuerySortingException;

/**
 * Used for building {@link ExecutableObjectifyQuery} instances.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ObjectifyQueryRequestBuilder<T extends ObjectifyModel<T>>
        extends ObjectifyQueryRequest<T>, ObjectifyQueryRequestLimitedBuilder {

	public void addSimpleQueryFilter(ObjectifySimpleQueryFilter<T> filter);

	public void setSimpleQueryFilters(Iterable<ObjectifySimpleQueryFilter<T>> filters);

	public ObjectifyQueryRequestBuilder<T> copyBuilder();

	public ExecutableObjectifyQuery<T> buildExecutableQuery() throws InvalidQuerySortingException;

}
