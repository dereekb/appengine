package com.dereekb.gae.model.extension.search.document.components;

import java.util.Map;

import com.dereekb.gae.server.search.request.SearchServiceQueryOptions;
import com.dereekb.gae.server.search.request.SearchServiceQueryRequest;
import com.dereekb.gae.utilities.query.exception.IllegalQueryArgumentException;

/**
 * Used for initializing a model search further.
 *
 * @author dereekb
 *
 * @param <S>
 *            search type
 */
public interface ModelSearchInitializer<S extends ModelSearch> {

	/**
	 * Builds a {@link SearchServiceQueryRequest} using the input parameters.
	 *
	 * @param index
	 *            Requested index to target. Never {@code null}.
	 * @param searchOptions
	 *            Search Options. Never {@code null}.
	 * @param parameters
	 *            {@link Map} of optional parameters. Never {@code null}.
	 * @return {@link SearchServiceQueryRequest}. Never {@code null}.
	 * @throws IllegalQueryArgumentException
	 *             if the input parameters are rejected.
	 */
	public SearchServiceQueryRequest initalizeSearchRequest(String index,
	                                                        SearchServiceQueryOptions searchOptions,
	                                                        Map<String, String> parameters)
	        throws IllegalQueryArgumentException;

}
