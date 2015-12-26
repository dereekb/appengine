package com.dereekb.gae.web.api.model.extension.search;

import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.ApiResponseData;

public interface ApiSearchDelegateEntry {

	/**
	 * Performs a document search.
	 *
	 * @param request
	 *            {@link ApiSearchReadRequest}. Never {@code null}.
	 * @return {@link ApiResponse} to the request. Never {@code null}.
	 */
	public ApiResponseData search(ApiSearchReadRequest request);

	/**
	 * Performs a query search.
	 *
	 * @param request
	 *            {@link ApiSearchReadRequest}. Never {@code null}.
	 * @return {@link ApiResponse} to the request. Never {@code null}.
	 */
	public ApiResponseData query(ApiSearchReadRequest request);

	/**
	 * Updates the search index.
	 *
	 * @param request
	 *            {@link ApiSearchUpdateRequest} instance. Never {@code null}.
	 */
	public void updateSearchIndex(ApiSearchUpdateRequest request);

}
