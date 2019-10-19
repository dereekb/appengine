package com.dereekb.gae.server.search.components;

/**
 * Interface for objects that are associated with a specific search index.
 *
 * @author dereekb
 *
 */
public interface SearchServiceIndex {

	/**
	 * Returns the name of the search index to target
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getIndexName();

}
