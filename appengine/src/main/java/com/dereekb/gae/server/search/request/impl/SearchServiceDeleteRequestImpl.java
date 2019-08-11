package com.dereekb.gae.server.search.request.impl;

import com.dereekb.gae.server.search.request.SearchServiceDeleteRequest;

/**
 * {@link SearchServiceDeleteRequest} implementation
 *
 * @author dereekb
 *
 */
public class SearchServiceDeleteRequestImpl extends SearchServiceKeysRequestImpl
        implements SearchServiceDeleteRequest {

	public SearchServiceDeleteRequestImpl(String indexName, Iterable<String> documentKeys)
	        throws IllegalArgumentException {
		super(indexName, documentKeys);
		this.setDocumentKeys(documentKeys);
	}

	@Override
	public String toString() {
		return "SearchServiceDeleteRequestImpl [documentKeys=" + this.getDocumentKeys() + ", getIndexName()="
		        + this.getIndexName() + "]";
	}

}
