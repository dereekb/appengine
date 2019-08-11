package com.dereekb.gae.server.search.request;

/**
 * Base request for the Search Document API.
 * <p>
 * Requests target a specific index.
 *
 * @author dereekb
 *
 */
public interface SearchServiceRequest {

	/**
	 * Returns the name of the search index to target
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getIndexName();

}
