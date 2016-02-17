package com.dereekb.gae.web.api.model.extension.search;

import java.util.Map;

import com.dereekb.gae.server.search.model.SearchOptions;

/**
 * Represents a request made to the {@link SearchExtensionApiController} to search
 * for models.
 *
 * @author dereekb
 *
 */
public interface ApiSearchReadRequest
        extends SearchOptions {

	/**
	 * Returns the query parameter.
	 *
	 * @return the query parameter. Never {@code null} or an empty string,
	 *         unless {@link getParameters} is to be used instead.
	 */
	public String getQuery();

	/**
	 * Whether or not to return keys instead of models
	 *
	 * @return {@code true} if only keys should be returned.
	 */
	public boolean getKeysOnly();

	/**
	 * Returns any custom parameters.
	 * <p>
	 * Generally used only for searching single elements.
	 *
	 * @return {@link Map} of parameters. {@link null} if none are specified.
	 */
	public Map<String, String> getParameters();

}
