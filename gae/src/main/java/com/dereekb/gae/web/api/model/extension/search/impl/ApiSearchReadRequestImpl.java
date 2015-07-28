package com.dereekb.gae.web.api.model.extension.search.impl;

import java.util.Map;
import java.util.Set;

import com.dereekb.gae.web.api.model.extension.search.ApiSearchReadRequest;


/**
 * {@link ApiSearchReadRequest} implementation.
 *
 * @author dereekb
 *
 */
public class ApiSearchReadRequestImpl
        implements ApiSearchReadRequest {

	private String query;
	private Integer limit;
	private Set<String> searchTypes;
	private Map<String, String> parameters;

	public ApiSearchReadRequestImpl() {}

	public ApiSearchReadRequestImpl(String query,
	        Integer limit,
	        Set<String> searchTypes,
 Map<String, String> parameters)
	        throws IllegalArgumentException {
		this.setQuery(query);
		this.setLimit(limit);
		this.setSearchTypes(searchTypes);
		this.setParameters(parameters);
	}

	@Override
	public String getQuery() {
		return this.query;
	}

	public void setQuery(String query) throws IllegalArgumentException {
		if (query == null || query.isEmpty()) {
			throw new IllegalArgumentException("Query cannot be null.");
		}

		this.query = query;
	}

	@Override
	public Integer getLimit() {
		return this.limit;
	}

	public void setLimit(Integer limit) throws IllegalArgumentException {
		if (limit != null && limit < 1) {
			throw new IllegalArgumentException("Must specify a positive, non-zero limit.");
		}

		this.limit = limit;
	}

	@Override
	public Set<String> getSearchTypes() {
		return this.searchTypes;
	}

	public void setSearchTypes(Set<String> searchTypes) throws IllegalArgumentException {
		if (searchTypes != null && searchTypes.isEmpty()) {
			throw new IllegalArgumentException("Search types cannot be empty. Set null if none specified.");
		}

		this.searchTypes = searchTypes;
	}

	@Override
	public Map<String, String> getParameters() {
		return this.parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	@Override
	public String toString() {
		return "ApiSearchReadRequestImpl [query=" + this.query + ", limit=" + this.limit + ", searchTypes="
		        + this.searchTypes + ", parameters=" + this.parameters + "]";
	}

}
