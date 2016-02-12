package com.dereekb.gae.web.api.model.extension.search.impl;

import java.util.Map;

import com.dereekb.gae.server.search.model.impl.SearchOptionsImpl;
import com.dereekb.gae.web.api.model.extension.search.ApiSearchReadRequest;


/**
 * {@link ApiSearchReadRequest} implementation.
 *
 * @author dereekb
 *
 */
public class ApiSearchReadRequestImpl extends SearchOptionsImpl
        implements ApiSearchReadRequest {

	private String query;
	private boolean models;
	private Map<String, String> parameters;

	public ApiSearchReadRequestImpl() {}

	public ApiSearchReadRequestImpl(Map<String, String> parameters, Integer limit) {
		this.setParameters(parameters);
		this.setLimit(limit);
	}

	public ApiSearchReadRequestImpl(String query, Integer limit) {
		this(query, limit, null, null);
	}

	public ApiSearchReadRequestImpl(String query, Integer limit, Map<String, String> parameters) {
		this(query, limit, parameters, null);
	}

	public ApiSearchReadRequestImpl(String query, Integer limit, Map<String, String> parameters, String cursor)
	        throws IllegalArgumentException {

		if (query == null || query.isEmpty()) {
			throw new IllegalArgumentException("Query cannot be null.");
		}

		this.setQuery(query);
		this.setLimit(limit);
		this.setParameters(parameters);
		this.setCursor(cursor);
	}

	@Override
	public String getQuery() {
		return this.query;
	}

	public void setQuery(String query) {
		this.query = query;
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
