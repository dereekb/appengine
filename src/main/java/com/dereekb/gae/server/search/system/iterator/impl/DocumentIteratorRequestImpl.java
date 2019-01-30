package com.dereekb.gae.server.search.system.iterator.impl;

import com.dereekb.gae.server.search.system.iterator.DocumentIteratorRequest;
import com.dereekb.gae.server.search.system.request.impl.DocumentRangeReadRequestImpl;

/**
 * {@link DocumentIteratorRequest} implementation.
 *
 * @author dereekb
 *
 */
public class DocumentIteratorRequestImpl extends DocumentRangeReadRequestImpl
        implements DocumentIteratorRequest {

	private Integer batchSize;

	public DocumentIteratorRequestImpl(String indexName) throws IllegalArgumentException {
		super(indexName, null);
	}

	public DocumentIteratorRequestImpl(String indexName, String documentIdentifier) throws IllegalArgumentException {
		super(indexName, documentIdentifier);
	}

	@Override
	public Integer getBatchSize() {
		return this.batchSize;
	}

	public void setBatchSize(Integer batchSize) {
		this.batchSize = batchSize;
	}

}
