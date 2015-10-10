package com.dereekb.gae.model.extension.search.document.index.component;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.search.DocumentChangeModel;
import com.dereekb.gae.server.search.UniqueSearchModel;

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
public interface IndexingDocument<T extends UniqueSearchModel> extends DocumentChangeModel {

	/**
	 * Returns the model.
	 *
	 * @return Model associated with this document. Never {@code null}.
	 */
	public T getDocumentModel();

	/**
	 * Returns the identifier set when the document is saved.
	 *
	 * @return {@link String} of the saved identifier, or {@code null} if the
	 *         document has not yet been modified.
	 */
	public String getSavedIdentifier();

}
