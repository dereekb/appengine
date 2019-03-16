package com.dereekb.gae.utilities.collections.pool;

/**
 * Used for accessing {@link QueryEntity} values within a {@link QueryPool}.
 * <p>
 * This accessor abstracts the methodologies in which matches are picked. It
 * will return results in the order in which it chooses, and may restrict the
 * total number of results returned (instead of allow iteration over the entire
 * pool).
 * 
 * @author dereekb
 *
 */
public interface QueryPoolEntityAccessor
        extends QueryPoolAssociate {

	/**
	 * Whether or not the pool is empty of results.
	 * 
	 * @return {@code true} if no entities are in the associated
	 *         {@link QueryPool}.
	 */
	public boolean isEmpty();

	/**
	 * Returns an {@link Iterable} of matching entities.
	 * <p>
	 * This function is may not always return the same results.
	 * 
	 * @return {@link Iterable}. Never {@code null}.
	 */
	public Iterable<QueryPoolEntity> getEntities();

}
