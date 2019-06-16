package com.dereekb.gae.web.api.model.extension.search;

import com.dereekb.gae.web.api.model.extension.search.impl.ApiSearchResponseData;
import com.dereekb.gae.web.api.shared.response.ApiResponse;

/**
 *
 * @author dereekb
 *
 * @see {@link ApiSearchDelegate}
 */
public interface ApiSearchDelegateEntry {

	/**
	 * Performs a document search.
	 *
	 * @param request
	 *            {@link ApiSearchReadRequest}. Never {@code null}.
	 * @return {@link ApiResponse} to the request. Never {@code null}.
	 * @deprecated
	 */
	@Deprecated
	public ApiSearchResponseData search(ApiSearchReadRequest request);

	/**
	 * Performs a query search.
	 *
	 * @param request
	 *            {@link ApiSearchReadRequest}. Never {@code null}.
	 * @return {@link ApiResponse} to the request. Never {@code null}.
	 */
	public ApiSearchResponseData query(ApiSearchReadRequest request);

	/**
	 * Updates the search index.
	 *
	 * @param request
	 *            {@link ApiSearchUpdateRequest} instance. Never {@code null}.
	 */
	public void updateSearchIndex(ApiSearchUpdateRequest request);

}
