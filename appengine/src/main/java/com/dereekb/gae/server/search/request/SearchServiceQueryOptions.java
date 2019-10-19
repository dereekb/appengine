package com.dereekb.gae.server.search.request;

import java.util.Collection;

import com.dereekb.gae.utilities.model.search.request.SearchOptions;

/**
 * {@link SearchOptions} extension for {@link SearchServiceQuerier}.
 *
 * @author dereekb
 *
 */
public interface SearchServiceQueryOptions
        extends SearchOptions {

	/**
	 * Returns the set of document fields to return.
	 *
	 * @return {@link Collection}, or {@code null} if no restrictions.
	 */
	public Collection<String> getFieldsToReturn();

}
