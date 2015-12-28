package com.dereekb.gae.model.extension.search.document.index.component;

import com.dereekb.gae.server.search.UniqueSearchModel;
import com.dereekb.gae.server.search.system.exception.DocumentPutException;

/**
 * Used for updating the search index with {@link IndexingDocument} values.
 *
 * @author dereekb
 */
@Deprecated
public interface DocumentIndexer<T extends UniqueSearchModel> {

	/**
	 * Indexes the documents.
	 *
	 * @param documents
	 *            Documents to index. Never {@code null}.
	 */
	public void indexDocuments(Iterable<IndexingDocument<T>> documents) throws DocumentPutException;

	/**
	 * Updates the documents.
	 * <p>
	 * All input documents should already be indexed.
	 *
	 * @param documents
	 *            Documents to index. Never {@code null}.
	 */
	public void updateDocuments(Iterable<IndexingDocument<T>> documents) throws DocumentPutException;

	/**
	 * Deletes the search documents for each of the models.
	 * <p>
	 * Models are also cleared of their identifiers.
	 *
	 * @param models
	 *            Models to remove from the index. Never {@code null}.
	 */
	public void deleteDocuments(Iterable<T> models);

}
