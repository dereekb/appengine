package com.dereekb.gae.server.search.system.request.impl;

import com.dereekb.gae.server.deprecated.search.system.request.SearchDocumentRequest;

/**
 * {@link SearchDocumentRequest} implementation.
 *
 * @author dereekb
 *
 */
public class SearchDocumentRequestImpl
        implements SearchDocumentRequest {

	private String indexName;

	public SearchDocumentRequestImpl(String indexName) throws IllegalArgumentException {
		this.indexName = indexName;
	}

	@Override
	public String getIndexName() {
		return this.indexName;
	}

	public void setIndexName(String indexName) throws IllegalArgumentException {
		if (indexName == null) {
			throw new IllegalArgumentException("Index name cannot be null.");
		}

		this.indexName = indexName;
	}

}
