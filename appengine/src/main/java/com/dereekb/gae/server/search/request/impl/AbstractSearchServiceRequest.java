package com.dereekb.gae.server.search.request.impl;

import com.dereekb.gae.server.search.request.SearchServiceRequest;
import com.dereekb.gae.utilities.data.StringUtility;

/**
 * Abstract {@link SearchServiceRequest} implementation.
 *
 * @author dereekb
 *
 */
public abstract class AbstractSearchServiceRequest
        implements SearchServiceRequest {

	private String indexName;

	public AbstractSearchServiceRequest(String indexName) throws IllegalArgumentException {
		this.indexName = indexName;
	}

	@Override
	public String getIndexName() {
		return this.indexName;
	}

	public void setIndexName(String indexName) throws IllegalArgumentException {
		if (StringUtility.isEmptyString(indexName)) {
			throw new IllegalArgumentException("Index name cannot be null or empty.");
		}

		this.indexName = indexName;
	}

}
