package com.dereekb.gae.server.datastore.objectify.query;

import com.dereekb.gae.server.datastore.objectify.query.order.ObjectifyQueryOrdering;
import com.dereekb.gae.server.datastore.objectify.query.order.ObjectifyQueryOrderingChain;

/**
 * Limited version of a {@link ObjectifyQueryRequestBuilder}.
 *
 * @author dereekb
 *
 */
public interface ObjectifyQueryRequestLimitedBuilder {

	public void setOptions(ObjectifyQueryRequestOptions options);

	public void setResultsOrdering(ObjectifyQueryOrderingChain orderingChain);

	public void addResultsOrdering(ObjectifyQueryOrdering ordering);

	public void addQueryFilter(ObjectifyQueryFilter filter);

	public void setQueryFilters(Iterable<ObjectifyQueryFilter> filters);

}
