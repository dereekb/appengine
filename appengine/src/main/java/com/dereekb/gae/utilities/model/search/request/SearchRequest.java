package com.dereekb.gae.utilities.model.search.request;

import java.util.Map;

/**
 * Model search request.
 * 
 * @author dereekb
 *
 */
public interface SearchRequest
        extends ParameterSearchOptions {

	/**
	 * Whether or not the request should only return matching keys.
	 * 
	 * @return {@code true} if keys-only search.
	 */
	public boolean isKeysOnly();

	/**
	 * Gets all search parameters.
	 * 
	 * @return {@link Map}. Never {@code null}.
	 */
	public Map<String, String> getSearchParameters();

}
