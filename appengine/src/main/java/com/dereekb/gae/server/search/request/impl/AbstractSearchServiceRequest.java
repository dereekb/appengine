package com.dereekb.gae.server.search.request.impl;

import com.dereekb.gae.server.search.components.impl.SearchServiceIndexImpl;
import com.dereekb.gae.server.search.request.SearchServiceRequest;

/**
 * Abstract {@link SearchServiceRequest} implementation.
 *
 * @author dereekb
 *
 */
public abstract class AbstractSearchServiceRequest extends SearchServiceIndexImpl {

	public AbstractSearchServiceRequest(String indexName) throws IllegalArgumentException {
		super(indexName);
	}

}
