package com.dereekb.gae.model.extension.search.document.index.component;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.search.DocumentChangeModel;
import com.dereekb.gae.server.search.UniqueSearchModel;
import com.dereekb.gae.utilities.collections.pairs.HandlerPair;
import com.google.appengine.api.search.Document;

/**
 * Wraps a {@link UniqueModel} with a document to be used for indexing.
 *
 * If the indexing document is saved, the model will be updated.
 *
 * @author dereekb
 *
 * @param <T>
 *            {@link UniqueSearchModel} model.
 */
public final class IndexingDocument<T extends UniqueSearchModel> extends HandlerPair<T, Document>
        implements DocumentChangeModel {

	private String savedIdentifier;

	public IndexingDocument(Document document, T model) {
		super(model, document);
	}

	public T getModel() {
		return this.key;
	}

	@Override
    public Document getDocument() {
		return this.object;
	}

	@Override
    public String toString() {
		return "IndexingDocument [model=" + this.key + ", document=" + this.object + "]";
    }

	/**
	 * @return Returns the identifier set when the document is saved. Is null if
	 *         the document has not yet been modified.
	 */
	public String getSavedIdentifier() {
		return this.savedIdentifier;
	}

	@Override
	public void savedWithId(String identifier) {
		this.savedIdentifier = identifier;
	}


}
