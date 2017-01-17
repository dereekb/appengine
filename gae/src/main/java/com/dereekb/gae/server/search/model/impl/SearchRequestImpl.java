package com.dereekb.gae.server.search.model.impl;

import java.util.HashMap;
import java.util.Map;

import com.dereekb.gae.utilities.model.search.request.MutableSearchRequest;
import com.dereekb.gae.utilities.model.search.request.SearchOptions;
import com.dereekb.gae.utilities.model.search.request.SearchRequest;

/**
 * {@link SearchRequest} implementation.
 * 
 * @author dereekb
 *
 */
public class SearchRequestImpl extends SearchOptionsImpl
        implements MutableSearchRequest {

	public static final String SEARCH_PARAMETERS = "keysOnly";

	private boolean keysOnly;
	private Map<String, String> searchParameters;

	public SearchRequestImpl() {}

	public SearchRequestImpl(SearchRequest request) {
		if (request == null) {
			throw new IllegalArgumentException("Request cannot be null.");
		}

		this.setKeysOnly(request.isKeysOnly());
		this.setSearchParameters(request.getSearchParameters());
		this.setOptions(request);
	}

	public SearchRequestImpl(boolean keysOnly) {
		this(keysOnly, null);
	}

	public SearchRequestImpl(boolean keysOnly, Map<String, String> searchParameters) throws IllegalArgumentException {
		this.setSearchParameters(searchParameters);
	}

	public SearchRequestImpl(boolean keysOnly, Map<String, String> searchParameters, SearchOptions options) {
		this.setKeysOnly(keysOnly);
		this.setSearchParameters(searchParameters);

		if (options != null) {
			this.setOptions(options);
		}
	}

	protected SearchRequestImpl(Map<String, String> rawParameters) throws IllegalArgumentException {
		this.setParameters(rawParameters);
	}

	// MARK: MutableSearchRequest
	@Override
	public boolean isKeysOnly() {
		return this.keysOnly;
	}

	@Override
	public void setKeysOnly(boolean keysOnly) {
		this.keysOnly = keysOnly;
	}

	@Override
	public Map<String, String> getSearchParameters() {
		return this.searchParameters;
	}

	@Override
	public void setSearchParameters(Map<String, String> searchParameters) {
		if (searchParameters == null) {
			searchParameters = new HashMap<String, String>();
		}

		this.searchParameters = searchParameters;
	}

	// MARK: Parameters
	@Override
	public Map<String, String> getParameters() {
		Map<String, String> parameters = new HashMap<String, String>(this.searchParameters);

		parameters.putAll(super.getParameters());

		return parameters;
	}

	@Override
	public void setParameters(Map<String, String> parameters) throws IllegalArgumentException {
		if (parameters == null) {
			throw new IllegalArgumentException("Parameters cannot be null.");
		}

		super.setParameters(parameters);
		this.searchParameters = new HashMap<String, String>(parameters);
	}

	@Override
	public String toString() {
		return "ApiSearchReadRequestImpl [keysOnly=" + this.keysOnly + ", searchParameters=" + this.searchParameters
		        + ", cursor=" + this.getCursor() + ", offset=" + this.getOffset() + ", limit=" + this.getLimit() + "]";
	}

}
