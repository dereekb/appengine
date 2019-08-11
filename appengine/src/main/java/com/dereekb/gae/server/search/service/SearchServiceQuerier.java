package com.dereekb.gae.server.search.service;

import com.dereekb.gae.server.search.request.SearchServiceQueryRequest;
import com.dereekb.gae.server.search.response.SearchServiceQueryResponse;

/**
 * Services for searching documents via queries.
 *
 * @author dereekb
 *
 */
public interface SearchServiceQuerier {

	/**
	 * Queries for documents.
	 *
	 * @param request
	 *            {@link SearchServiceQueryRequest}. Never {@code null}.
	 * @return {@link SearchServiceQueryResponse}. Never {@code null}.
	 */
	public SearchServiceQueryResponse queryIndex(SearchServiceQueryRequest request);

}
