package com.dereekb.gae.model.extension.search.query.service.impl;

import java.util.Map;

import com.dereekb.gae.model.extension.search.query.service.ModelQueryRequest;
import com.dereekb.gae.server.search.model.impl.SearchRequestImpl;
import com.dereekb.gae.utilities.model.search.request.SearchOptions;
import com.dereekb.gae.utilities.model.search.request.SearchRequest;

/**
 * {@link ModelQueryRequest} implementation.
 *
 * @author dereekb
 *
 */
public class ModelQueryRequestImpl extends SearchRequestImpl
        implements ModelQueryRequest {

	public ModelQueryRequestImpl() {
		super();
	}

	public ModelQueryRequestImpl(boolean keysOnly, Map<String, String> searchParameters, SearchOptions options) {
		super(keysOnly, searchParameters, options);
	}

	public ModelQueryRequestImpl(boolean keysOnly, Map<String, String> searchParameters)
	        throws IllegalArgumentException {
		super(keysOnly, searchParameters);
	}

	public ModelQueryRequestImpl(boolean keysOnly) {
		super(keysOnly);
	}

	public ModelQueryRequestImpl(SearchRequest request) {
		super(request);
	}

}
