package com.dereekb.gae.server.datastore.objectify.query;

import com.googlecode.objectify.cmd.Query;

/**
 * Filter for an Objectify {@link Query}.
 *
 * @author dereekb
 *
 */
public interface ObjectifyQueryFilter {

	/**
	 * Applies a filter to the query, and returns the result.
	 *
	 * @param query
	 *            {@link Query}. Never {@code null}.
	 * @return {@link Query}. Never {@code null}.
	 */
	public <T> Query<T> filter(Query<T> query);

}
