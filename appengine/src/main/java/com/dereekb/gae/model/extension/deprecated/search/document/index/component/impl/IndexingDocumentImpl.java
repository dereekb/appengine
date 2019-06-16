package com.dereekb.gae.model.extension.search.document.index.component.impl;

import com.dereekb.gae.model.extension.deprecated.search.document.index.component.IndexingDocument;
import com.dereekb.gae.server.search.UniqueSearchModel;
import com.dereekb.gae.utilities.collections.pairs.impl.HandlerPair;
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

	public IndexingDocumentImpl(Document document, T model) {
		super(model, document);
	}

	// MARK: IndexingDocument
	@Override
    public T getDocumentModel() {
		return this.key;
	}

	@Override
    public Document getDocument() {
		return this.object;
	}

	@Override
	public String getSearchIdentifier() {
		return this.key.getSearchIdentifier();
	}

	@Override
	public void setSearchIdentifier(String identifier) {
		this.key.setSearchIdentifier(identifier);
	}

	@Override
	public String toString() {
		return "IndexingDocument [model=" + this.key + ", document=" + this.object + "]";
	}

}
