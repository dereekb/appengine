package com.dereekb.gae.web.api.model.extension.search;

import com.dereekb.gae.model.extension.read.exception.UnavailableTypeException;
import com.dereekb.gae.web.api.shared.response.ApiResponse;

/**
 * {@link SearchApiExtensionController} delegate.
 *
 * @author dereekb
 *
 */
public interface ApiSearchDelegate {

	/**
	 * Searches a single type.
	 *
	 * @param type
	 *            type to search
	 * @param request
	 *            {@link ApiSearchReadRequest} containing search parameters.
	 * @return {@link ApiResponse} to the request. Never {@code null}.
	 * @throws UnavailableTypeException
	 *             if the type is unknown/unavailable.
	 */
	public ApiResponse searchSingle(String type,
	                                ApiSearchReadRequest request) throws UnavailableTypeException;

	/**
	 * Searches multiple types.
	 *
	 * @param request
	 *            {@link ApiSearchReadRequest} containing search parameters.
	 * @return {@link ApiResponse} to the request. Never {@code null}.
	 * @throws UnavailableTypeException
	 *             if a specified type is unknown/unavailable.
	 */
	public ApiResponse searchMultiple(ApiSearchReadRequest request) throws UnavailableTypeException;

	/**
	 * Queries a single type.
	 *
	 * @param type
	 *            type to query
	 * @param request
	 *            {@link ApiSearchReadRequest} containing query parameters.
	 * @return {@link ApiResponse} to the request. Never {@code null}.
	 * @throws UnavailableTypeException
	 *             if the type is unknown/unavailable.
	 */
	public ApiResponse querySingle(String type,
	                                ApiSearchReadRequest request) throws UnavailableTypeException;

	/**
	 * Updates the search index.
	 *
	 * @param request
	 *            {@link ApiSearchUpdateRequest} instance. Never {@code null}.
	 * @return {@link ApiResponse} to the request. Never {@code null}.
	 * @throws UnavailableTypeException
	 *             if a specified type is unknown/unavailable.
	 */
	public ApiResponse updateSearchIndex(ApiSearchUpdateRequest request) throws UnavailableTypeException;

}
