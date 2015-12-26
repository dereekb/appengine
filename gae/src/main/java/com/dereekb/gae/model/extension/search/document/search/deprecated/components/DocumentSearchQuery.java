package com.dereekb.gae.model.extension.search.document.search.deprecated.components;

import com.dereekb.gae.model.extension.search.document.search.SearchPair;


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
@Deprecated
public interface DocumentSearchQuery {

	/**
	 * @return Returns the limit of elements.
	 */
	public Integer getLimit();

}
