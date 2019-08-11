package com.dereekb.gae.server.search.service;

import com.dereekb.gae.server.search.request.SearchServiceReadRequest;
import com.dereekb.gae.server.search.response.SearchServiceReadResponse;

/**
 * Services for reading documents via their identifiers.
 *
 * @author dereekb
 */
public interface SearchServiceReader {

	// public SearchServiceReadResponse
	// readDocuments(SearchServiceRangeReadRequest request);

	/**
	 * Document read request.
	 *
	 * @param request
	 *            {@link (SearchServiceReadRequest}. Never {@code null}.
	 * @return {@link SearchServiceReadResponse}. Never {@code null}.
	 */
	public SearchServiceReadResponse readDocuments(SearchServiceReadRequest request);

}
