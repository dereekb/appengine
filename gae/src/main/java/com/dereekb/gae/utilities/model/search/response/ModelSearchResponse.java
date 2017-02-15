package com.dereekb.gae.utilities.model.search.response;

import java.util.Collection;

import com.dereekb.gae.utilities.model.search.exception.KeysOnlySearchException;
import com.dereekb.gae.utilities.model.search.request.SearchResponse;

/**
 * Contains model objects within the repsonse.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ModelSearchResponse<T>
        extends SearchResponse {

	/**
	 * Whether or not the response responded in a keys-only fashion. This is
	 * generally decided by the original request.
	 */
	public boolean isKeysOnlyResponse();

	/**
	 * Returns the model search results.
	 *
	 * @return {@link Collection} of results. Never {@code null}.
	 * @throws KeysOnlySearchException
	 *             if {@link #isKeysOnlyResponse()} is true and th
	 *             implementation cannot return models post-search.
	 */
	public Collection<T> getModelResults() throws KeysOnlySearchException;

}
