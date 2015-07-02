package com.dereekb.gae.model.extension.search.document.index.component;

import com.dereekb.gae.server.search.UniqueSearchModel;

/**
 * Used for building an indexing document
 *
 * @author dereekb
 *
 * @param <T>
 */
public interface IndexingDocumentBuilder<T extends UniqueSearchModel> {

	/**
	 * Creates a new {@link IndexingDocument} for the passed model.
	 */
	public IndexingDocument<T> buildSearchDocument(T model);

}
