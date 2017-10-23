package com.dereekb.gae.utilities.query.builder.parameters;

import com.dereekb.gae.utilities.query.order.QueryResultsOrdering;

/**
 * Parameter with an {@link QueryResultsOrdering}.
 * 
 * @author dereekb
 *
 */
public interface OrderedQueryParameter {

	/**
	 * Returns the ordering specified by the parameter.
	 * 
	 * @return {@link QueryResultsOrdering}, or {@code null} if not defined.
	 */
	public QueryResultsOrdering getOrdering();

}
