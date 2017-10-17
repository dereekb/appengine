package com.dereekb.gae.server.datastore.objectify.query.order;

/**
 * Query ordering used by Objectify queries.
 *
 * @author dereekb
 *
 */
public interface ObjectifyQueryOrdering {

	/**
	 * Returns the field being sorted.
	 * 
	 * @return {@link String}. Never {@code null}.
	 */
	public String getField();

	/**
	 * Returns the full ordering string.
	 * 
	 * @return {@link String}. Never {@code null}.
	 */
	public String getOrderingString();

}
