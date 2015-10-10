package com.dereekb.gae.model.extension.search.document.index.component.impl;

import com.dereekb.gae.model.extension.search.document.index.component.IndexingDocument;
import com.dereekb.gae.server.search.UniqueSearchModel;
import com.dereekb.gae.utilities.collections.pairs.HandlerPair;
import com.google.appengine.api.search.Document;

/**
 * {@link IndexingDocument} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public final class IndexingDocumentImpl<T extends UniqueSearchModel> extends HandlerPair<T, Document>
        implements IndexingDocument<T> {

	private String savedIdentifier;

	public IndexingDocumentImpl(Document document, T model) {
		super(model, document);
	}

	@Override
    public T getDocumentModel() {
		return this.key;
	}

	@Override
    public Document getDocument() {
		return this.object;
	}

	@Override
    public String getSavedIdentifier() {
		return this.savedIdentifier;
	}

	@Override
	public void savedWithId(String identifier) {
		this.savedIdentifier = identifier;
	}

	@Override
	public String toString() {
		return "IndexingDocument [model=" + this.key + ", document=" + this.object + "]";
	}

}
