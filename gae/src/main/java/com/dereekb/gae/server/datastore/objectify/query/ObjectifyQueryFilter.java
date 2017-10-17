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
	 * Returns the field for this filter.
	 * 
	 * @return {@link String}. Never {@code null}.
	 */
	public String getField();

	/**
	 * Whether or not this filter is an inequality filter.
	 * 
	 * @return {@code true} if its an inequality.
	 */
	public boolean isInequality();

	/**
	 * Applies a filter to the query, and returns the result.
	 *
	 * @param query
	 *            {@link Query}. Never {@code null}.
	 * @return {@link Query}. Never {@code null}.
	 */
	public <T> Query<T> filter(Query<T> query);

}
