package com.dereekb.gae.web.api.model.extension.search.impl;

import java.util.Map;

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
	private boolean models;
	private Map<String, String> parameters;

	public ApiSearchReadRequestImpl() {}

	public ApiSearchReadRequestImpl(String query, Integer limit, Map<String, String> parameters)
	        throws IllegalArgumentException {
		this.setQuery(query);
		this.setLimit(limit);
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
	public boolean getModels() {
		return this.models;
	}

	public void setModels(boolean models) {
		this.models = models;
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
		return "ApiSearchReadRequestImpl [query=" + this.query + ", limit=" + this.limit + ", models=" + this.models
		        + ", parameters=" + this.parameters + "]";
	}

}
