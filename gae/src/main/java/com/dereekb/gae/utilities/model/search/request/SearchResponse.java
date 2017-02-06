package com.dereekb.gae.utilities.model.search.request;

import java.util.Collection;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Generic model response to a {@link SearchRequest}.
 * 
 * @author dereekb
 * @see {@link ModelSearchRequest} for model results.
 */
public interface SearchResponse {

	/**
	 * Returns the cursor to continue the request.
	 * 
	 * @return {@link String}. {@code null} if end of the index.
	 */
	public String getSearchCursor();

	/**
	 * Returns the {@link ModelKey} search results.
	 *
	 * @return {@link Collection} of results. Never {@code null}.
	 */
	public Collection<ModelKey> getKeyResults();

}