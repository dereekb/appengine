package com.dereekb.gae.model.extension.search.document.index.component.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.dereekb.gae.model.extension.search.document.index.component.IndexingDocument;
import com.dereekb.gae.model.extension.search.document.index.component.IndexingDocumentSet;
import com.dereekb.gae.server.search.UniqueSearchModel;
import com.dereekb.gae.server.search.service.request.DocumentPutRequestModel;

/**
 * {@link IndexingDocumentSet} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class IndexingDocumentSetImpl<T extends UniqueSearchModel>
        implements IndexingDocumentSet<T> {

	private boolean update = false;
	private String indexName;
	private Collection<IndexingDocument<T>> documents;

	public IndexingDocumentSetImpl() {};

	public IndexingDocumentSetImpl(String indexName, Collection<IndexingDocument<T>> documents)
	        throws IllegalArgumentException {
		this.indexName = indexName;
		this.documents = documents;
	}

	@Override
	public boolean isUpdate() {
		return this.update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	@Override
	public String getIndexName() {
		return this.indexName;
	}

	public void setIndexName(String indexName) throws IllegalArgumentException {
		if (indexName == null) {
			throw new IllegalArgumentException("Index cannot be null.");
		}

		this.indexName = indexName;
	}

	public Collection<IndexingDocument<T>> getDocuments() {
		return this.documents;
	}

	public void setDocuments(Collection<IndexingDocument<T>> documents) throws IllegalArgumentException {
		if (documents == null) {
			throw new IllegalArgumentException("Documents cannot be null.");
		}

		this.documents = documents;
	}

	// MARK: IndexingDocumentSet
	@Override
	public Collection<DocumentPutRequestModel> getPutRequestModels() {
		return new ArrayList<DocumentPutRequestModel>(this.documents);
	}

}
