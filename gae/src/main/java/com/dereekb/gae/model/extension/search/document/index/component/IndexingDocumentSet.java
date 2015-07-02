package com.dereekb.gae.model.extension.search.document.index.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.server.search.DocumentChangeModel;
import com.dereekb.gae.server.search.DocumentChangeSet;
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
public final class IndexingDocumentSet<T extends UniqueSearchModel>
        implements DocumentChangeSet {

	private boolean success = false;
	private final String index;
	private final Collection<IndexingDocument<T>> documents;

	public IndexingDocumentSet(String index, Collection<IndexingDocument<T>> documents) {
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

	public boolean getSuccess() {
		return this.success;
	}

	@Override
	public void setSuccess(boolean success) {
		this.success = success;
	}

}
