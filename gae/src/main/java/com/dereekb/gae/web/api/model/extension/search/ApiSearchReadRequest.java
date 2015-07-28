package com.dereekb.gae.web.api.model.extension.search;

import java.util.Map;
import java.util.Set;

/**
 * Represents a request made to the {@link SearchApiExtensionController} to search
 * for models.
 *
 * @author dereekb
 *
 */
public interface ApiSearchReadRequest {

	/**
	 * Returns the query parameter.
	 *
	 * @return the query parameter. Never {@code null} or empty unless
	 *         {@link getParameters} is to be used primarily.
	 */
	public String getQuery();

	/**
	 * Returns the max number of elements to retrieve.
	 *
	 * @return the limit. {@link null} if not specified.
	 */
	public Integer getLimit();

	/**
	 * Returns the model types to search.
	 * <p>
	 * Used for searching multiple values at once.
	 *
	 * @return types to search. Never {@code null} or empty.
	 */
	public Set<String> getSearchTypes();

	/**
	 * Returns any custom parameters.
	 * <p>
	 * Generally used only for searching single elements.
	 *
	 * @return {@link Map} of parameters. {@link null} if none are specified.
	 */
	public Map<String, String> getParameters();

}
