package com.dereekb.gae.utilities.collections.pool;

import com.dereekb.gae.utilities.collections.pool.exception.UnknownPoolEntityTypeException;

/**
 * Abstract pool that contains multiple entities that are accessed through
 * {@link QueryPool}.
 * <p>
 * This is used in cases where the pool of matches contains multiple different
 * entity types.
 * 
 * @author dereekb
 *
 */
public interface MatchPool
        extends Pool<MatchPool> {

	/**
	 * Returns a {@link QueryPool} for the input type.
	 * <p>
	 * The new pool is filtered to these types exclusively.
	 * 
	 * @param entityType
	 *            {@link String}. Never {@code null}.
	 * @return {@link QueryPool}. Never {@code null}.
	 */
	public QueryPool getQueryPoolForEntityTypes(String entityType) throws UnknownPoolEntityTypeException;

}
