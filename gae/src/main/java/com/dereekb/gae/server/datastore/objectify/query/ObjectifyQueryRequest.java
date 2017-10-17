package com.dereekb.gae.server.datastore.objectify.query;

import java.util.List;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.query.order.ObjectifyQueryOrdering;
import com.dereekb.gae.utilities.collections.chain.Chain;

/**
 * Utility wrapper for performing queries with Objectify.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ObjectifyQueryRequest<T extends ObjectifyModel<T>> {

	public ObjectifyQueryRequestOptions getOptions();

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

	public Chain<ObjectifyQueryOrdering> getResultsOrdering();

}
