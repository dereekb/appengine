package com.dereekb.gae.web.api.model.extension.search.impl;

import java.util.Collections;
import java.util.Map;

import com.dereekb.gae.server.search.model.impl.SearchRequestImpl;
import com.dereekb.gae.web.api.model.extension.search.ApiSearchReadRequest;

/**
 * {@link ApiSearchReadRequest} implementation.
 *
 * @author dereekb
 *
 */
public class ApiSearchReadRequestImpl extends SearchRequestImpl
        implements ApiSearchReadRequest {

	private String query;

	public ApiSearchReadRequestImpl() {}

	public ApiSearchReadRequestImpl(Map<String, String> parameters) {
		this.setParameters(parameters);
	}

	public ApiSearchReadRequestImpl(String query) throws IllegalArgumentException {
		this(query, Collections.<String, String> emptyMap());
	}

	public ApiSearchReadRequestImpl(String query, Map<String, String> rawParameters) throws IllegalArgumentException {
		super(rawParameters);
		this.setQuery(query);
	}

	@Override
	public String getQuery() {
		return this.query;
	}

	public void setQuery(String query) {
		if (query == null) {
			throw new IllegalArgumentException("Query cannot be null.");
		}

		this.query = query;
	}

	@Override
	public void setParameters(Map<String, String> parameters) throws IllegalArgumentException {
		if (parameters == null) {
			parameters = Collections.emptyMap();
		}

		super.setParameters(parameters);
	}

}
