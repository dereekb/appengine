package com.dereekb.gae.utilities.collections.pool;

/**
 * Abstract pool of {@link QueryEntity} values.
 * <p>
 * Pools can be arbitrarily large, and this interface provides high-level
 * accessors. Pools are also immutable, meaning these are only designed to be
 * used as an interface for reading/retrieving information.
 * <p>
 * Query pools inherently already imply a subset of models, so {@link QueryPool}
 * implementations will generally already have queries built into them.
 * 
 * @author dereekb
 *
 */
public interface QueryPool extends Pool<QueryPool> {

	/**
	 * Returns the entity type.
	 * 
	 * @return {@link String}. Never {@code null}.
	 */
	public String getEntityType();

	/**
	 * Returns an {@link QueryPoolEntityAccessor} for this pool.
	 * 
	 * @return {@link QueryPoolEntityAccessor}. Never {@code null}.
	 */
	public QueryPoolEntityAccessor getEntityAccessor();

}
