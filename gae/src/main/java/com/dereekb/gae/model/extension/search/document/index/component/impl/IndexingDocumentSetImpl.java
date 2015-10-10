package com.dereekb.gae.model.extension.search.document.index.component.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.extension.search.document.index.component.IndexingDocument;
import com.dereekb.gae.model.extension.search.document.index.component.IndexingDocumentSet;
import com.dereekb.gae.server.search.DocumentChangeModel;
import com.dereekb.gae.server.search.UniqueSearchModel;

/**
 * Wraps a collection of {@link IndexingDocument} instances with the name of the
 * index.
 *
 * @author dereekb
 *
 * @param <T>
 *            {@link UniqueSearchModel} model.
 */
public final class IndexingDocumentSetImpl<T extends UniqueSearchModel>
        implements IndexingDocumentSet<T> {

	private boolean success = false;

	private final String index;
	private final Collection<IndexingDocument<T>> documents;

	public IndexingDocumentSetImpl(String index, Collection<IndexingDocument<T>> documents) {
		this.index = index;
		this.documents = documents;
	}

	public String getIndex() {
		return this.index;
	}

	public Collection<IndexingDocument<T>> getDocuments() {
		return this.documents;
	}

	@Override
	public List<DocumentChangeModel> getDocumentModels() {
		List<DocumentChangeModel> documents = new ArrayList<DocumentChangeModel>();

		for (IndexingDocument<T> document : this.documents) {
			documents.add(document);
		}

		return documents;
	}

	@Override
	public String getIndexName() {
		return this.index;
	}

	@Override
	public boolean getSuccess() {
		return this.success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public String toString() {
		return "IndexingDocumentSetImpl [success=" + this.success + ", index=" + this.index + ", documents="
		        + this.documents + "]";
	}

}
