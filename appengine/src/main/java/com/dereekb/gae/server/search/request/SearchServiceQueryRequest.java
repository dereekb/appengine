package com.dereekb.gae.server.search.request;

import com.dereekb.gae.server.search.query.SearchServiceQueryExpression;
import com.dereekb.gae.utilities.model.search.request.SearchOptions;

/**
 * Used for performing queries on the service.
 *
 * @author dereekb
 *
 */
public interface SearchServiceQueryRequest
        extends SearchServiceRequest {

	/**
	 * Returns the search options.
	 *
	 * @return {@link SearchOptions}. Never {@code null}.
	 */
	public SearchServiceQueryOptions getSearchOptions();

	/**
	 * Returns the query expression.
	 *
	 * @return {@link SearchServiceQueryExpression}, or {@code null} if none.
	 */
	public SearchServiceQueryExpression getExpression();

}
