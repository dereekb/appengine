package com.dereekb.gae.model.extension.search.document.index.component.impl;

import java.util.List;

import com.dereekb.gae.model.extension.search.document.index.component.DocumentIndexer;
import com.dereekb.gae.model.extension.search.document.index.component.IndexingDocument;
import com.dereekb.gae.server.search.UniqueSearchModel;
import com.dereekb.gae.server.search.system.SearchDocumentIndexSystem;
import com.dereekb.gae.server.search.system.SearchDocumentSystem;
import com.dereekb.gae.server.search.system.request.impl.DocumentIdentifierRequestImpl;
import com.dereekb.gae.utilities.collections.IteratorUtility;

/**
 * {@link DocumentIndexer} implementation.
 *
 * @author dereekb
 * @param <T>
 *            model type
 */
@Deprecated
public class DocumentIndexerImpl<T extends UniqueSearchModel>
        implements DocumentIndexer<T> {

	private String indexName;
	private SearchDocumentSystem indexService;

	public DocumentIndexerImpl(String indexName, SearchDocumentSystem indexService) {
		this.indexName = indexName;
		this.indexService = indexService;
	}

	public String getIndexName() {
		return this.indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public SearchDocumentIndexSystem getIndexService() {
		return this.indexService;
	}

	public void setIndexService(SearchDocumentSystem indexService) {
		this.indexService = indexService;
	}

	// MARK: DocumentIndexer
	@Override
	public void indexDocuments(Iterable<IndexingDocument<T>> documents) {
		List<IndexingDocument<T>> docList = IteratorUtility.iterableToList(documents);
		IndexingDocumentSetImpl<T> docSet = new IndexingDocumentSetImpl<T>(this.indexName, docList);
		docSet.setUpdate(false);
		this.indexService.put(docSet);
	}

	@Override
	public void updateDocuments(Iterable<IndexingDocument<T>> documents) {
		List<IndexingDocument<T>> docList = IteratorUtility.iterableToList(documents);
		IndexingDocumentSetImpl<T> docSet = new IndexingDocumentSetImpl<T>(this.indexName, docList);
		docSet.setUpdate(true);
		this.indexService.put(docSet);
	}

	@Override
	public void deleteDocuments(Iterable<T> models) {

		DocumentIdentifierRequestImpl request = new DocumentIdentifierRequestImpl(this.indexName, null);
		this.indexService.deleteDocuments(request);
	}

	/*
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

	}

	@Override
	public boolean deleteDocuments(Iterable<T> models) {
		DocumentSearchController.deleteWithSearchables(models, this.index, false);

		for (T model : models) {
			model.setSearchIdentifier(null);
		}

		return true;
	}
	*/

}
