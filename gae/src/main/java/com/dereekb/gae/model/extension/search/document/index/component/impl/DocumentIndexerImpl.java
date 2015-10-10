package com.dereekb.gae.model.extension.search.document.index.component.impl;

import java.util.List;

import com.dereekb.gae.model.extension.search.document.index.component.DocumentIndexer;
import com.dereekb.gae.model.extension.search.document.index.component.IndexingDocument;
import com.dereekb.gae.model.extension.search.document.index.component.IndexingDocumentSet;
import com.dereekb.gae.server.search.DocumentSearchController;
import com.dereekb.gae.server.search.UniqueSearchModel;
import com.dereekb.gae.utilities.collections.IteratorUtility;

/**
 * {@link DocumentIndexer} implementation.
 *
 * @author dereekb
 * @param <T>
 *            model type
 */
public class DocumentIndexerImpl<T extends UniqueSearchModel>
        implements DocumentIndexer<T> {

	private String index;

	public DocumentIndexerImpl(String index) {
		this.index = index;
	}

	public String getIndex() {
		return this.index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	@Override
	public boolean indexDocuments(Iterable<IndexingDocument<T>> documents) {
		return this.indexDocuments(documents, false);
	}

	@Override
	public boolean updateDocuments(Iterable<IndexingDocument<T>> documents) {
		return this.indexDocuments(documents, true);
	}

	private boolean indexDocuments(Iterable<IndexingDocument<T>> documents,
	                               boolean update) {
		List<IndexingDocument<T>> docList = IteratorUtility.iterableToList(documents);
		IndexingDocumentSet<T> docSet = new IndexingDocumentSetImpl<T>(this.index, docList);
		DocumentSearchController.put(docSet, update);
		return docSet.getSuccess();
	}

	@Override
	public boolean deleteDocuments(Iterable<T> models) {
		DocumentSearchController.deleteWithSearchables(models, this.index, false);

		for (T model : models) {
			model.setSearchIdentifier(null);
		}

		return true;
	}

}
