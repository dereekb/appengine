package com.dereekb.gae.utilities.model.search.request;

import java.util.Map;

import com.dereekb.gae.utilities.misc.parameters.Parameters;

/**
 * {@link SearchRequest} extension that can be modified.
 * 
 * @author dereekb
 *
 */
public interface MutableSearchRequest
        extends SearchRequest, MutableParameterSearchOptions {

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

	/**
	 * Sets the search parameters only.
	 * 
	 * @param searchParameters
	 *            {@link Parameters}. Never {@code null}.
	 */
	public void setSearchParameters(Parameters searchParameters);

}
