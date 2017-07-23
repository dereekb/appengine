package com.dereekb.gae.web.api.model.extension.search;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.extension.links.service.exception.LinkServiceChangeSetException;
import com.dereekb.gae.web.api.exception.ApiIllegalArgumentException;
import com.dereekb.gae.web.api.exception.resolver.RuntimeExceptionResolver;
import com.dereekb.gae.web.api.model.exception.resolver.AtomicOperationFailureResolver;
import com.dereekb.gae.web.api.model.extension.search.impl.ApiSearchReadRequestImpl;
import com.dereekb.gae.web.api.model.extension.search.impl.ApiSearchUpdateRequestImpl;
import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.google.appengine.api.datastore.DatastoreNeedIndexException;

/**
 * Controller for the Search API components.
 *
 * @author dereekb
 *
 */
@RestController
public class SearchExtensionApiController {

	private ApiSearchDelegate delegate;

	public SearchExtensionApiController(ApiSearchDelegate delegate) {
		this.setDelegate(delegate);
	}

	public ApiSearchDelegate getDelegate() {
		return this.delegate;
	}

	public void setDelegate(ApiSearchDelegate delegate) {
		if (delegate == null) {
			throw new IllegalArgumentException("Delegate cannot be null.");
		}

		this.delegate = delegate;
	}

	// MARK: Search
	/**
	 * Searches multiple types at once.
	 *
	 * @param query
	 *            search query
	 * @param types
	 *            types to search. Never {@code null} or empty.
	 * @param limit
	 *            optional limit
	 * @param parameters
	 *            optional parameters
	 * @return {@link ApiResponse}. Never {@code null}.
	 */
	@ResponseBody
	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
	public final ApiResponse searchMultiple(@RequestParam @NotEmpty String query,
	                                        @RequestParam @NotEmpty Set<String> types,
	                                        @RequestParam Map<String, String> parameters,
	                                        @RequestParam(required = false) Integer limit,
	                                        @RequestParam(required = false, defaultValue = "true") Boolean keysOnly) {
		ApiResponse response = null;

		try {
			ApiSearchReadRequestImpl request = new ApiSearchReadRequestImpl();
			request.setParameters(parameters);
			request.setQuery(query);

			request.setLimit(limit);
			request.setKeysOnly(keysOnly);

			response = this.delegate.search(types, request);
		} catch (AtomicOperationException e) {
			AtomicOperationFailureResolver.resolve(e);
		} catch (IllegalArgumentException e) {
			throw new ApiIllegalArgumentException(e);
		} catch (RuntimeException e) {
			RuntimeExceptionResolver.resolve(e);
		}

		return response;
	}

	/**
	 * Performs a search on a single type. The search is potentially more
	 * complex.
	 *
	 * @param type
	 *            type to search. Never {@code null}.
	 * @param query
	 *            string query. Never {@code null}.
	 * @param limit
	 *            Optional retrieval limit
	 * @param parameters
	 *            Optional search parameters
	 * @return {@link ApiResponse} with all returned models.
	 */
	@ResponseBody
	@RequestMapping(value = "/{type}/search", method = RequestMethod.GET, produces = "application/json")
	public final ApiResponse searchSingle(@PathVariable("type") String type,
	                                      @RequestParam Map<String, String> parameters,
	                                      @RequestParam(required = false) Integer limit,
	                                      @RequestParam(required = false, defaultValue = "true") Boolean keysOnly) {
		ApiResponse response = null;

		try {
			ApiSearchReadRequestImpl request = new ApiSearchReadRequestImpl();

			request.setParameters(parameters);
			request.setLimit(limit);
			request.setKeysOnly(keysOnly);

			response = this.delegate.search(type, request);
		} catch (AtomicOperationException e) {
			AtomicOperationFailureResolver.resolve(e);
		} catch (RuntimeException e) {
			RuntimeExceptionResolver.resolve(e);
		}

		return response;
	}

	// MARK: Query
	/**
	 * Performs a model query.
	 *
	 * @param type
	 *            model type
	 * @param limit
	 *            model limit
	 * @param parameters
	 *            query parameters
	 * @return {@link ApiResponse}. Never {@code null}.
	 */
	@ResponseBody
	@RequestMapping(value = "/{type}/query", method = RequestMethod.GET, produces = "application/json")
	public final ApiResponse querySingle(@PathVariable("type") String type,
	                                     @RequestParam Map<String, String> parameters,
	                                     @RequestParam(required = false) Integer limit,
	                                     @RequestParam(required = false, defaultValue = "true") Boolean keysOnly) {
		ApiResponse response = null;

		try {
			ApiSearchReadRequestImpl request = new ApiSearchReadRequestImpl();

			request.setParameters(parameters);
			request.setLimit(limit);
			request.setKeysOnly(keysOnly);

			response = this.delegate.query(type, request);
		} catch (LinkServiceChangeSetException e) {
			throw e;
		} catch (AtomicOperationException e) {
			AtomicOperationFailureResolver.resolve(e);
		} catch (IllegalArgumentException e) {
			throw new ApiIllegalArgumentException(e);
		} catch (DatastoreNeedIndexException e) {
			throw e;
		} catch (RuntimeException e) {
			RuntimeExceptionResolver.resolve(e);
		}

		return response;
	}

	// MARK: Update
	@ResponseBody
	@RequestMapping(value = "/{type}/search/index", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public final ApiResponse updateIndex(@PathVariable("type") String type,
	                                     @RequestParam List<String> keys) {
		ApiResponse response = null;

		try {
			ApiSearchUpdateRequestImpl request = new ApiSearchUpdateRequestImpl();
			request.setType(type);
			request.setTargetKeys(keys);

			response = this.delegate.updateSearchIndex(request);
		} catch (AtomicOperationException e) {
			AtomicOperationFailureResolver.resolve(e);
		} catch (RuntimeException e) {
			RuntimeExceptionResolver.resolve(e);
		}

		return response;
	}

	@Override
	public String toString() {
		return "SearchExtensionApiController [delegate=" + this.delegate + "]";
	}

}
