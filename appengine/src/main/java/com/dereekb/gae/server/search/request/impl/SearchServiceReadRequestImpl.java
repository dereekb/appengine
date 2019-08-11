package com.dereekb.gae.server.search.request.impl;

import com.dereekb.gae.server.search.request.SearchServiceReadRequest;

/**
 * {@link SearchServiceReadRequest} implementation
 *
 * @author dereekb
 *
 */
public class SearchServiceReadRequestImpl extends SearchServiceKeysRequestImpl
        implements SearchServiceReadRequest {

	public SearchServiceReadRequestImpl(String indexName, Iterable<String> documentKeys)
	        throws IllegalArgumentException {
		super(indexName, documentKeys);
		this.setDocumentKeys(documentKeys);
	}

	@Override
	public String toString() {
		return "SearchServiceReadRequestImpl [documentKeys=" + this.getDocumentKeys() + ", getIndexName()="
		        + this.getIndexName() + "]";
	}

}
