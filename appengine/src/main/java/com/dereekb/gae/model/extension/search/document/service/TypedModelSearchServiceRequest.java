package com.dereekb.gae.model.extension.search.document.service;

import com.dereekb.gae.utilities.model.search.request.SearchRequest;

/**
 * Request for a {@link TypedModelSearchService}
 *
 * @author dereekb
 *
 */
public interface TypedModelSearchServiceRequest
        extends SearchRequest {

	/**
	 * Returns the index if provided.
	 *
	 * @return {@link String} or {@code null} if not provided.
	 */
	public String getIndex();

}
