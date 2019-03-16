package com.dereekb.gae.utilities.model.search.request;

import java.util.Collection;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.iterator.cursor.ResultsCursor;

/**
 * Generic model response to a {@link SearchRequest}.
 * 
 * @author dereekb
 * @see ModelSearchRequest
 */
public interface SearchResponse {

	/**
	 * Returns the cursor to continue the request.
	 * 
	 * @return {@link String}. {@code null} if unavailable.
	 */
	public ResultsCursor getSearchCursor();

	/**
	 * Returns the {@link ModelKey} search results.
	 *
	 * @return {@link Collection} of results. Never {@code null}.
	 */
	public Collection<ModelKey> getKeyResults();

}
