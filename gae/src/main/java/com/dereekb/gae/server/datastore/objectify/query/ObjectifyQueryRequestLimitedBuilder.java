package com.dereekb.gae.server.datastore.objectify.query;

import com.dereekb.gae.server.datastore.objectify.query.exception.TooManyQueryInequalitiesException;
import com.dereekb.gae.server.datastore.objectify.query.order.ObjectifyQueryOrdering;
import com.dereekb.gae.utilities.collections.chain.Chain;

/**
 * Limited version of a {@link ObjectifyQueryRequestBuilder}.
 *
 * @author dereekb
 *
 */
public interface ObjectifyQueryRequestLimitedBuilder {

	public void setOptions(ObjectifyQueryRequestOptions options);

	public void setResultsOrdering(Chain<ObjectifyQueryOrdering> orderingChain);

	public void addResultsOrdering(ObjectifyQueryOrdering ordering);

	public void addQueryFilter(ObjectifyQueryFilter filter) throws TooManyQueryInequalitiesException;

	public void setQueryFilters(Iterable<ObjectifyQueryFilter> filters) throws TooManyQueryInequalitiesException;

}
