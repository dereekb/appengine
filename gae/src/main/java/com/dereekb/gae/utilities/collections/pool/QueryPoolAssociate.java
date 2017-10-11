package com.dereekb.gae.utilities.collections.pool;

/**
 * Object that is related to a {@link QueryPool}.
 * 
 * @author dereekb
 *
 */
public interface QueryPoolAssociate {

	/**
	 * Returns the pool this item is associated with.
	 * 
	 * @return {@link QueryPool}. Never {@code null}.
	 */
	public QueryPool getPool();

}
