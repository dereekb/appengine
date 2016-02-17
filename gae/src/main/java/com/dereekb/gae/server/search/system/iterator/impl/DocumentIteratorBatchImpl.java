package com.dereekb.gae.server.search.system.iterator.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.server.search.system.iterator.DocumentIteratorBatch;
import com.google.appengine.api.search.Document;


public class DocumentIteratorBatchImpl
        implements DocumentIteratorBatch {

	private final List<Document> documents;
	private List<String> identifiers;

	public DocumentIteratorBatchImpl(List<Document> documents) throws IllegalArgumentException {
		if (documents == null || documents.isEmpty()) {
			throw new IllegalArgumentException("Documents must be non-null and not empty.");
		}

		this.documents = documents;
	}

	@Override
	public List<String> getIdentifiers() {
		if (this.identifiers == null) {
			this.identifiers = this.buildIdentifiers();
		}

		return this.identifiers;
	}

	private List<String> buildIdentifiers() {
		List<String> identifiers = new ArrayList<String>();

		for (Document document : this.documents) {
			identifiers.add(document.getId());
		}

		return identifiers;
	}

	@Override
	public List<Document> getDocuments() {
		return this.documents;
	}

	@Override
	public Integer getBatchSize() {
		return this.documents.size();
	}

	@Override
    public Document getLastDocument() {
		Integer size = this.getBatchSize();
		Document document = this.documents.get(size - 1);
		return document;
	}

	@Override
	public String getLastIdentifier() {
		Document document = this.getLastDocument();
		return document.getId();
	}

}
