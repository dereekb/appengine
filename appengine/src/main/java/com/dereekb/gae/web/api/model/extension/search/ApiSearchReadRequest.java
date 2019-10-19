package com.dereekb.gae.web.api.model.extension.search;

import com.dereekb.gae.utilities.model.search.request.SearchRequest;

/**
 * Represents a request made to the {@link SearchExtensionApiController} to
 * search
 * for models.
 *
 * @author dereekb
 *
 */
public interface ApiSearchReadRequest
        extends SearchRequest {

	/**
	 * Returns the index to search.
	 *
	 * @return {@link String} or {@code null} if default search.
	 */
	public String getIndex();

	/**
	 * Returns the query parameter.
	 *
	 * @return the query parameter. Never {@code null} or an empty string,
	 *         unless {@link getParameters} is to be used instead.
	 */
	public String getQuery();

}
