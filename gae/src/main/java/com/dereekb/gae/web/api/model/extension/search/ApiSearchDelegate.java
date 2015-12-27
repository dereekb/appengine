package com.dereekb.gae.web.api.model.extension.search;

import java.util.Set;

import com.dereekb.gae.model.extension.read.exception.UnavailableTypesException;
import com.dereekb.gae.web.api.shared.response.ApiResponse;

/**
 * {@link SearchExtensionApiController} delegate.
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
	 * @throws UnavailableTypesException
	 *             if the type is unknown/unavailable.
	 */
	public ApiResponse search(String type,
	                          ApiSearchReadRequest request) throws UnavailableTypesException;

	/**
	 * Searches multiple types.
	 *
	 * @param types
	 * @param request
	 *            {@link ApiSearchReadRequest} containing search parameters.
	 * @return {@link ApiResponse} to the request. Never {@code null}.
	 * @throws UnavailableTypesException
	 *             if a specified type is unknown/unavailable.
	 */
	public ApiResponse search(Set<String> types,
	                          ApiSearchReadRequest request) throws UnavailableTypesException;

	/**
	 * Queries a single type.
	 *
	 * @param type
	 *            type to query
	 * @param request
	 *            {@link ApiSearchReadRequest} containing query parameters.
	 * @return {@link ApiResponse} to the request. Never {@code null}.
	 * @throws UnavailableTypesException
	 *             if the type is unknown/unavailable.
	 */
	public ApiResponse query(String type,
	                         ApiSearchReadRequest request) throws UnavailableTypesException;

	/**
	 * Updates the search index.
	 *
	 * @param request
	 *            {@link ApiSearchUpdateRequest} instance. Never {@code null}.
	 * @return {@link ApiResponse} to the request. Never {@code null}.
	 * @throws UnavailableTypesException
	 *             if a specified type is unknown/unavailable.
	 */
	public ApiResponse updateSearchIndex(ApiSearchUpdateRequest request) throws UnavailableTypesException;

}
