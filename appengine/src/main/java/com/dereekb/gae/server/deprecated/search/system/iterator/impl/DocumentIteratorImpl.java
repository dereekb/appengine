package com.dereekb.gae.server.search.system.iterator.impl;

import java.util.List;

import com.dereekb.gae.server.deprecated.search.system.exception.DocumentIterationBoundsException;
import com.dereekb.gae.server.deprecated.search.system.iterator.DocumentIterator;
import com.dereekb.gae.server.deprecated.search.system.iterator.DocumentIteratorBatch;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.GetRequest;
import com.google.appengine.api.search.GetResponse;
import com.google.appengine.api.search.Index;

/**
 * {@link DocumentIterator} implementation.
 *
 * @author dereekb
 *
 */
public class DocumentIteratorImpl
        implements DocumentIterator {

	private Index index;
	private Integer batchSize;
	private boolean idsOnly = false;

	private boolean finished = false;
	private String identifier;

	private GetRequest request;

	public DocumentIteratorImpl(Index index, Integer batchSize, boolean idsOnly) {
		this.index = index;
		this.batchSize = batchSize;
		this.idsOnly = idsOnly;
	}

	@Override
	public String getIndexName() {
		return this.index.getName();
	}

	@Override
	public DocumentIteratorBatch getNextBatch() throws DocumentIterationBoundsException {
		List<Document> documents = this.getNextDocuments();
		DocumentIteratorBatch batch;

		try {
			batch = new DocumentIteratorBatchImpl(documents);
			this.identifier = batch.getLastIdentifier();
		} catch (IllegalArgumentException e) {
			this.finished = true;
			throw new DocumentIterationBoundsException();
		}

		return batch;
	}

	private List<Document> getNextDocuments() {
		List<Document> documents;

		if (!this.finished) {
			GetRequest request = this.getNextRequest();
			GetResponse<Document> response = this.index.getRange(request);
			documents = response.getResults();
		} else {
			documents = null;
		}

		return documents;
	}

	private GetRequest getNextRequest() {
		GetRequest.Builder builder = this.getNextBuilder();
		return builder.build();
	}

	private GetRequest.Builder getNextBuilder() {
		GetRequest.Builder builder = GetRequest.newBuilder().setReturningIdsOnly(this.idsOnly);

		if (this.request == null) {
			builder = builder.setIncludeStart(true);
		}

		if (this.identifier != null) {
			builder = builder.setStartId(this.identifier);
		}

		if (this.batchSize != null) {
			builder = builder.setLimit(this.batchSize);
		}

		return builder;
	}

}
