package com.dereekb.gae.model.extension.search.document.index.component;

import com.dereekb.gae.server.search.UniqueSearchModel;

/**
 * Used to index pairs within a specific index.
 *
 * @author dereekb
 */
public interface DocumentIndexer<T extends UniqueSearchModel> {

	/**
	 * Indexes the documents and updates the IndexingDocumentl with the set
	 * identifier.
	 *
	 * @param documents
	 * @return True if successful.
	 */
	public boolean indexDocuments(Iterable<IndexingDocument<T>> documents);

	/**
	 * Updates the documents. The action does not guarantee the input
	 * {@link IndexingDocument} instances to be updated. For guarantee, use the
	 * indexDocuments() function.
	 *
	 * @param documents
	 * @return True if successful.
	 */
	public boolean updateDocuments(Iterable<IndexingDocument<T>> documents);

	/**
	 * Deletes the search documents for each of the models.
	 *
	 * @param models
	 * @return True if successful.
	 */
	public boolean deleteDocuments(Iterable<T> models);

}
