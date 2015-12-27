package com.dereekb.gae.web.api.model.extension.search;

import java.util.Map;

/**
 * Represents a request made to the {@link SearchExtensionApiController} to search
 * for models.
 *
 * @author dereekb
 *
 */
public interface ApiSearchReadRequest {

	/**
	 * Returns the query parameter.
	 *
	 * @return the query parameter. Never {@code null} or an empty string,
	 *         unless {@link getParameters} is to be used instead.
	 */
	public String getQuery();

	/**
	 * Returns the max number of elements to retrieve.
	 *
	 * @return the limit. {@link null} if not specified.
	 */
	public Integer getLimit();

	/**
	 *
	 * @return {@link String} of the cursor if available.
	 */
	public String getCursor();

	/**
	 * Whether or not to return models instead of identifiers.
	 *
	 * @return {@code true} if models should be loaded.
	 */
	public boolean getModels();

	/**
	 * Returns any custom parameters.
	 * <p>
	 * Generally used only for searching single elements.
	 *
	 * @return {@link Map} of parameters. {@link null} if none are specified.
	 */
	public Map<String, String> getParameters();

}
