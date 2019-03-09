package com.dereekb.gae.utilities.model.search.request;

import java.util.Map;

/**
 * {@link SearchRequest} extension that can be modified.
 * 
 * @author dereekb
 *
 */
public interface MutableSearchRequest
        extends SearchRequest, MutableSearchOptions {

	/**
	 * Sets the type of request.
	 * 
	 * @param keysOnly
	 */
	public void setKeysOnly(boolean keysOnly);

	/**
	 * Sets the search parameters only, instead of all parameters like
	 * {@link #setParameters(Map)}.
	 * 
	 * @param searchParameters
	 *            {@link Map}. Can be {@code null}.
	 */
	public void setSearchParameters(Map<String, String> searchParameters) throws IllegalArgumentException;

}
