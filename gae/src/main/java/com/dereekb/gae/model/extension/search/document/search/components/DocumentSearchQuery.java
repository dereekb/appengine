package com.dereekb.gae.model.extension.search.document.search.components;


/**
 * Query used in {@link SearchPair} and {@link DocumentSearchFunction}
 * instances.
 *
 * Is used by the {@link DocumentSearchQueryFactory} to convert this query to a
 * string, and then searches the database.
 *
 * @author dereekb
 *
 * @param <T>
 */
public interface DocumentSearchQuery {

	/**
	 * @return Returns the limit of elements.
	 */
	public Integer getLimit();

}
