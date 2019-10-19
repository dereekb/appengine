package com.dereekb.gae.server.search.request.impl;

import com.dereekb.gae.server.search.request.SearchServiceKeysRequest;

/**
 * {@link SearchServiceKeysRequest} implementation
 *
 * @author dereekb
 *
 */
public class SearchServiceKeysRequestImpl extends AbstractSearchServiceRequest
        implements SearchServiceKeysRequest {

	private Iterable<String> documentKeys;

	public SearchServiceKeysRequestImpl(SearchServiceKeysRequest keysRequest)
	        throws IllegalArgumentException {
		this(keysRequest.getIndexName(), keysRequest.getDocumentKeys());
	}

	public SearchServiceKeysRequestImpl(String indexName, Iterable<String> documentKeys)
	        throws IllegalArgumentException {
		super(indexName);
		this.setDocumentKeys(documentKeys);
	}

	@Override
	public Iterable<String> getDocumentKeys() {
		return this.documentKeys;
	}

	public void setDocumentKeys(Iterable<String> documentKeys) {
		if (documentKeys == null) {
			throw new IllegalArgumentException("documentKeys cannot be null.");
		}

		this.documentKeys = documentKeys;
	}

	@Override
	public String toString() {
		return "SearchServiceKeysRequestImpl [documentKeys=" + this.documentKeys + ", getIndexName()="
		        + this.getIndexName() + "]";
	}

}
