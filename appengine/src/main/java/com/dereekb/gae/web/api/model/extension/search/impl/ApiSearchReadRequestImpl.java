package com.dereekb.gae.web.api.model.extension.search.impl;

import java.util.Collections;
import java.util.Map;

import com.dereekb.gae.utilities.model.search.request.impl.SearchRequestImpl;
import com.dereekb.gae.web.api.model.extension.search.ApiSearchReadRequest;

/**
 * {@link ApiSearchReadRequest} implementation.
 *
 * @author dereekb
 *
 */
public class ApiSearchReadRequestImpl extends SearchRequestImpl
        implements ApiSearchReadRequest {

	private String index;
	private String query;

	public ApiSearchReadRequestImpl() {}

	public ApiSearchReadRequestImpl(Map<String, String> parameters) {
		this.setParameters(parameters);
	}

	public ApiSearchReadRequestImpl(String query) throws IllegalArgumentException {
		this(null, query);
	}

	public ApiSearchReadRequestImpl(String index, String query) throws IllegalArgumentException {
		this(index, query, Collections.<String, String> emptyMap());
	}

	public ApiSearchReadRequestImpl(String query, Map<String, String> rawParameters) throws IllegalArgumentException {
		this(null, query, rawParameters);
	}

	public ApiSearchReadRequestImpl(String index, String query, Map<String, String> rawParameters)
	        throws IllegalArgumentException {
		super(rawParameters);
		this.setIndex(index);
		this.setQuery(query);
	}

	@Override
	public String getIndex() {
		return this.index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	@Override
	public String getQuery() {
		return this.query;
	}

	public void setQuery(String query) {
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
